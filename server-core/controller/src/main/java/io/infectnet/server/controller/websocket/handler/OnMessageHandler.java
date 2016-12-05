package io.infectnet.server.controller.websocket.handler;

import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

@FunctionalInterface
public interface OnMessageHandler {
  void handle(Session session, String arguments) throws MalformedMessageException, IOException;
}
