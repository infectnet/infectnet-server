package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.controller.websocket.handler.OnCloseHandler;
import io.infectnet.server.controller.websocket.handler.OnConnectHandler;
import io.infectnet.server.controller.websocket.handler.OnMessageHandler;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

@WebSocket
public class Dispatcher {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    private final List<OnConnectHandler> onConnectHandlers;

    private final List<OnCloseHandler> onCloseHandlers;

    private final Map<SocketMessage.Action, OnMessageHandler> onMessageHandlerMap;

    private final JsonParser jsonParser;

    public Dispatcher(JsonParser jsonParser) {
        this.onConnectHandlers = new ArrayList<>();
        this.onCloseHandlers = new ArrayList<>();
        this.onMessageHandlerMap = new EnumMap<>(SocketMessage.Action.class);
        this.jsonParser = jsonParser;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        for(OnConnectHandler handler : onConnectHandlers){
            handler.handle(session);
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        for(OnCloseHandler handler : onCloseHandlers){
            handler.handle(session, statusCode, reason);
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try{
            SocketMessage socketMessage = getMessage(message);

            dispatch(session, socketMessage);
        } catch (MalformedMessageException e){
            //TODO exception handle
        }
    }

    private void dispatch(Session session, SocketMessage socketMessage) throws MalformedMessageException {
        OnMessageHandler handler = onMessageHandlerMap.get(socketMessage.getAction());

        try {
            handler.handle(session, socketMessage);
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    public void registerOnConnect(OnConnectHandler handler) {
        onConnectHandlers.add(Objects.requireNonNull(handler));
    }

    public void registerOnClose(OnCloseHandler handler) {
        onCloseHandlers.add(Objects.requireNonNull(handler));
    }

    public void registerOnMessage(SocketMessage.Action action, OnMessageHandler handler) {
        onMessageHandlerMap.put(Objects.requireNonNull(action), Objects.requireNonNull(handler));
    }

    private SocketMessage getMessage(String message) throws MalformedMessageException{
        try {
            JsonElement jsonElement = jsonParser.parse(message);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String actionStr = jsonObject.get("action").getAsString();
            String arguments = jsonObject.get("arguments").toString();

            return new SocketMessage(SocketMessage.Action.valueOf(actionStr), arguments);
        } catch (Exception e){
            throw new MalformedMessageException(e);
        }
    }
}
