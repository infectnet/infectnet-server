package io.infectnet.server.controller.websocket.subscribe;

import io.infectnet.server.controller.engine.EngineConnector;
import io.infectnet.server.controller.websocket.WebSocketController;
import io.infectnet.server.controller.websocket.WebSocketDispatcher;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.exception.AuthenticationNeededException;
import io.infectnet.server.controller.websocket.messaging.Action;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import io.infectnet.server.service.user.UserDTO;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * WebSocket controller responsible for handling user subscription.
 */
public class SubscriptionController implements WebSocketController {

  private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

  private final EngineConnector engineConnector;

  private final SessionAuthenticator sessionAuthenticator;

  private final MessageTransmitter messageTransmitter;

  public SubscriptionController(EngineConnector engineConnector,
                                SessionAuthenticator sessionAuthenticator,
                                MessageTransmitter messageTransmitter) {
    this.engineConnector = engineConnector;
    this.sessionAuthenticator = sessionAuthenticator;
    this.messageTransmitter = messageTransmitter;
  }

  @Override
  public void configure(WebSocketDispatcher webSocketDispatcher) {
    webSocketDispatcher.registerOnMessage(Action.SUBSCRIBE, this::handleUserSubscription);
    webSocketDispatcher.registerOnMessage(Action.UNSUBSCRIBE, this::handleUserUnsubscription);

    webSocketDispatcher.registerOnClose(this::autoUnsubscribeOnDisconnect);
  }

  private void handleUserSubscription(Session session, String arguments) throws IOException {
    Optional<UserDTO> user = sessionAuthenticator.verifyAuthentication(session);

    if (user.isPresent()) {
      engineConnector.setUserAsObserved(user.get());

      messageTransmitter.transmitString(session, SocketMessage.EMPTY_OK);

    } else {
      messageTransmitter.transmitException(session, new AuthenticationNeededException());
    }
  }

  private void handleUserUnsubscription(Session session, String arguments) throws IOException {
    Optional<UserDTO> user = sessionAuthenticator.verifyAuthentication(session);

    if (user.isPresent()) {
      engineConnector.removeUserFromObserved(user.get());

      messageTransmitter.transmitString(session, SocketMessage.EMPTY_OK);

    } else {
      messageTransmitter.transmitException(session, new AuthenticationNeededException());
    }
  }

  private void autoUnsubscribeOnDisconnect(Session session, int statusCode, String reason) {
    Optional<UserDTO> user = sessionAuthenticator.verifyAuthentication(session);

    if (user.isPresent()) {
      engineConnector.removeUserFromObserved(user.get());

      logger.info("Force unsubscribed user: {}", user.get());
    }
  }
}
