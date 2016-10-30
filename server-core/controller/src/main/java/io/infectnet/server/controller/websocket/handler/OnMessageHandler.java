package io.infectnet.server.controller.websocket.handler;

import org.eclipse.jetty.websocket.api.Session;

@FunctionalInterface
public interface OnMessageHandler {
    public void handle(Session session, String arguments);
}
