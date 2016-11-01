package io.infectnet.server.controller.websocket.handler;

import io.infectnet.server.controller.websocket.SocketMessage;
import io.infectnet.server.service.user.UserDTO;

@FunctionalInterface
public interface OnMessageHandler {
    void handle(UserDTO user, SocketMessage socketMessage);
}
