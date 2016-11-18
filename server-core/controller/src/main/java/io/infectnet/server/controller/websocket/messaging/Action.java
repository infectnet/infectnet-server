package io.infectnet.server.controller.websocket.messaging;

import io.infectnet.server.controller.websocket.WebSocketDispatcher;

/**
 * An enum for the different type of actions the User can make, which will go through the WebSocket,
 * and the {@link WebSocketDispatcher}
 */
public enum Action {
  AUTH,
  SUBSCRIBE,
  NEW_CODE,
  ERROR,
  OK
}
