package io.infectnet.server.controller.websocket.handler;

import org.eclipse.jetty.websocket.api.Session;

@FunctionalInterface
public interface OnCloseHandler {
    public void handle(Session session, int statusCode, String reason);
}
