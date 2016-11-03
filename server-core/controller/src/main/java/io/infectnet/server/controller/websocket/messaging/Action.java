package io.infectnet.server.controller.websocket.messaging;

/**
 * An enum for the different type of actions the User can make, which will go through the WebSocket,
 * and the {@link io.infectnet.server.controller.websocket.Dispatcher}
 */
public enum Action {
  AUTH,
  SUBSCRIBE,
  NEW_CODE,
  ERROR,
  OK
}
