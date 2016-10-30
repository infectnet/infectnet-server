package io.infectnet.server.controller.websocket.handler;

import org.eclipse.jetty.websocket.api.Session;

@FunctionalInterface
public interface OnCloseHandler {
    void handle(Session session, int statusCode, String reason);
}
