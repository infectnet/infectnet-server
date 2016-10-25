package io.infectnet.server.controller.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebSocketController {
  @OnWebSocketConnect
  public void connected(Session session) {
    /**
     * Placeholder method.
     */
    System.out.println("Someone connected!");
  }

  @OnWebSocketClose
  public void closed(Session session, int statusCode, String reason) {
    /**
     * Placeholder method.
     */
  }

  @OnWebSocketMessage
  public void message(Session session, String message) {
    System.out.println(message);
  }

}
