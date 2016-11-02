package io.infectnet.server.controller.websocket.handler;

import io.infectnet.server.controller.websocket.SocketMessage;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import org.eclipse.jetty.websocket.api.Session;

@FunctionalInterface
public interface OnMessageHandler {
    void handle(Session session, SocketMessage socketMessage) throws MalformedMessageException;
}
