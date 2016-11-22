package io.infectnet.server.controller.websocket.subscribe;

import io.infectnet.server.controller.engine.EngineConnector;
import io.infectnet.server.controller.websocket.WebSocketController;
import io.infectnet.server.controller.websocket.WebSocketDispatcher;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.code.CodeController;
import io.infectnet.server.service.user.UserDTO;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * WebSocket controller responsible for force unsubscribing disconnected users.
 */
public class UnsubscriberOnCloseController implements WebSocketController {

  private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

  private final EngineConnector engineConnector;

  private final SessionAuthenticator sessionAuthenticator;

  public UnsubscriberOnCloseController(
      EngineConnector engineConnector,
      SessionAuthenticator sessionAuthenticator) {
    this.engineConnector = engineConnector;
    this.sessionAuthenticator = sessionAuthenticator;
  }

  @Override
  public void configure(WebSocketDispatcher webSocketDispatcher) {
    webSocketDispatcher.registerOnClose(this::handleUnsubscriptionOnClose);
  }

  private void handleUnsubscriptionOnClose(Session session, int statusCode, String reason) {
    Optional<UserDTO> user = sessionAuthenticator.verifyAuthentication(session);

    if (user.isPresent()) {
      engineConnector.removePlayerFromObserved(user.get());

      logger.info("Force unsubscribed user: {}", user.get());
    }

  }
}
