package io.infectnet.server.controller.websocket.messaging;

import io.infectnet.server.controller.websocket.WebSocketDispatcher;

/**
 * An enum for the different type of actions the User can make, which will go through the WebSocket,
 * and the {@link WebSocketDispatcher}
 */
public enum Action {
  /**
   * Used when the client wants to authenticate itself.
   */
  AUTH,

  /**
   * Used when the client wants to receive map updates.
   */
  SUBSCRIBE,

  /**
   * Used when the client no longer wants to receive map updates.
   */
  UNSUBSCRIBE,

  /**
   * Used when the client uploads new code.
   */
  PUT_CODE,

  /**
   * Used when the client request its current code.
   */
  GET_CODE,

  /**
   * Used for showing the client something went wrong.
   */
  ERROR,

  /**
   * Used for showing the client that everything went ok.
   */
  OK,

  /**
   * Used for showing the client that the uploaded source code had any compilation errors.
   */
  COMPILATION_RESULTS,

  /**
   * Used for sending the client map updates.
   */
  STATUS_UPDATE,
}
