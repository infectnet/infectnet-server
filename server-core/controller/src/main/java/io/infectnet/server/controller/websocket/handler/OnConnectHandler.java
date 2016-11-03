package io.infectnet.server.controller.websocket.handler;

import org.eclipse.jetty.websocket.api.Session;

@FunctionalInterface
public interface OnConnectHandler {
  void handle(Session session);
}
