package io.infectnet.server.controller.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class WebSocketController {
  @OnWebSocketConnect
  public void connected(Session session) throws IOException {
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
  public void message(Session session, String message) throws IOException {
    System.out.println(message);
  }

}
