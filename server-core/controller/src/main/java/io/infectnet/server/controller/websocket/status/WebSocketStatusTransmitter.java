package io.infectnet.server.controller.websocket.status;


import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.messaging.Action;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.status.StatusMessage;
import io.infectnet.server.service.user.UserDTO;
import io.infectnet.server.service.user.UserService;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WebSocketStatusTransmitter {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketStatusTransmitter.class);

  private final MessageTransmitter messageTransmitter;

  private final SessionAuthenticator sessionAuthenticator;

  private final UserService userService;

  private final Map<Player, UserDTO> userDTOCache;

  public WebSocketStatusTransmitter(MessageTransmitter messageTransmitter,
                                    SessionAuthenticator sessionAuthenticator,
                                    UserService userService) {
    this.messageTransmitter = messageTransmitter;

    this.sessionAuthenticator = sessionAuthenticator;

    this.userService = userService;

    this.userDTOCache = new HashMap<>();
  }

  public void transmit(Player player, StatusMessage statusMessage) {

    SocketMessage<StatusMessage> message =
        new SocketMessage<>(Action.STATUS_UPDATE, statusMessage, StatusMessage.class);

    Optional<Session> session =
        getUserForPlayer(player).flatMap(sessionAuthenticator::getSessionForUserDto);

    if (session.isPresent()) {
      try {
        messageTransmitter.transmitString(session.get(), message);
      } catch (IOException e) {
        logger.warn("Couldn't send status update to {}: {}", player, e);
      }
    }

  }

  private Optional<UserDTO> getUserForPlayer(Player player) {
    UserDTO userDTO =
        userDTOCache.computeIfAbsent(player,
            p -> userService.getUserDtoByUsername(player.getUsername()).orElse(null));

    return Optional.ofNullable(userDTO);
  }

}
