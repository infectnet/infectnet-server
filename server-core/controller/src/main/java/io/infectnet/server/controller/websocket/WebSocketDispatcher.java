package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.infectnet.server.controller.error.ErrorConvertibleException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.controller.websocket.exception.UnsupportedActionException;
import io.infectnet.server.controller.websocket.handler.OnCloseHandler;
import io.infectnet.server.controller.websocket.handler.OnConnectHandler;
import io.infectnet.server.controller.websocket.handler.OnMessageHandler;
import io.infectnet.server.controller.websocket.messaging.Action;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Responsible for dispatching WebSocket events between registered handlers.
 */
@WebSocket
public class WebSocketDispatcher {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketDispatcher.class);

  private final List<OnConnectHandler> onConnectHandlers;

  private final List<OnCloseHandler> onCloseHandlers;

  private final Map<Action, OnMessageHandler> onMessageHandlerMap;

  private final JsonParser jsonParser;

  private final MessageTransmitter messageTransmitter;

  public WebSocketDispatcher(Set<WebSocketController> webSocketControllers, JsonParser jsonParser,
                             MessageTransmitter messageTransmitter) {
    this.onConnectHandlers = new ArrayList<>();
    this.onCloseHandlers = new ArrayList<>();
    this.onMessageHandlerMap = new EnumMap<>(Action.class);
    this.jsonParser = jsonParser;
    this.messageTransmitter = messageTransmitter;

    webSocketControllers.forEach(c -> {
      c.configure(this);
    });
  }

  @OnWebSocketConnect
  public void onConnect(Session session) {
    for (OnConnectHandler handler : onConnectHandlers) {
      handler.handle(session);
    }
  }

  @OnWebSocketClose
  public void onClose(Session session, int statusCode, String reason) {
    for (OnCloseHandler handler : onCloseHandlers) {
      handler.handle(session, statusCode, reason);
    }
  }

  @OnWebSocketMessage
  public void onMessage(Session session, String message) {
    try {
      RawMessage rawMessage = getMessage(message);

      dispatch(session, rawMessage);
    } catch (MalformedMessageException | UnsupportedActionException e) {
      reportDispatcherException(session, e);
    }
  }

  private void reportDispatcherException(Session session, ErrorConvertibleException exception) {
    try {
      messageTransmitter.transmitException(session, exception);
    } catch (IOException e) {
      logger.warn(e.toString());
    }
  }

  private void dispatch(Session session, RawMessage rawMessage)
      throws MalformedMessageException, UnsupportedActionException {
    if (!onMessageHandlerMap.containsKey(rawMessage.action)) {
      throw new UnsupportedActionException(rawMessage.action);
    }

    OnMessageHandler handler = onMessageHandlerMap.get(rawMessage.action);

    try {
      handler.handle(session, rawMessage.arguments);
    } catch (IOException e) {
      logger.warn(e.toString());
    }
  }

  /**
   * Registers an {@link OnConnectHandler} for WebSocket connection starts.
   * @param handler the handler
   */
  public void registerOnConnect(OnConnectHandler handler) {
    onConnectHandlers.add(Objects.requireNonNull(handler));
  }

  /**
   * Registers an {@link OnCloseHandler} for WebSocket connection close.
   * @param handler the handler
   */
  public void registerOnClose(OnCloseHandler handler) {
    onCloseHandlers.add(Objects.requireNonNull(handler));
  }

  /**
   * Registers an {@link OnMessageHandler} for the specified {@link Action}.
   * @param action the action the handler will listen to
   * @param handler the handler
   */
  public void registerOnMessage(Action action, OnMessageHandler handler) {
    onMessageHandlerMap.put(Objects.requireNonNull(action), Objects.requireNonNull(handler));
  }

  private RawMessage getMessage(String message) throws MalformedMessageException {
    try {
      JsonElement jsonElement = jsonParser.parse(message);

      JsonObject jsonObject = jsonElement.getAsJsonObject();

      String actionStr = jsonObject.get("action").getAsString();
      String arguments = jsonObject.get("arguments").toString();

      return new RawMessage(Action.valueOf(actionStr), arguments);
    } catch (Exception e) {
      throw new MalformedMessageException(e);
    }
  }

  private static class RawMessage {
    private final Action action;

    private final String arguments;

    private RawMessage(Action action, String arguments) {
      this.action = action;
      this.arguments = arguments;
    }
  }
}
