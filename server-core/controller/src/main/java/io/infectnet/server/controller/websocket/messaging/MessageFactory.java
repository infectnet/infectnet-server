package io.infectnet.server.controller.websocket.messaging;

public interface MessageFactory {
  <T> String convertSocketMessage(SocketMessage<T> socketMessage);
}
