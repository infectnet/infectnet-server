package io.infectnet.server.controller.websocket;

/**
 * Interface used for creating WebSocket endpoints.
 * All controllers using WebSocket should implement this interface.
 */
public interface WebSocketController {

  /**
   * Configures and registers itself in a {@link WebSocketDispatcher}.
   * @param webSocketDispatcher the dispatcher to register with
   */
  void configure(WebSocketDispatcher webSocketDispatcher);
}
