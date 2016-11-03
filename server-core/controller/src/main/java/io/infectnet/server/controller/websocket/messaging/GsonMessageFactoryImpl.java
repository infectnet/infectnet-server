package io.infectnet.server.controller.websocket.messaging;

import com.google.gson.Gson;

public class GsonMessageFactoryImpl implements MessageFactory {
  private final Gson gson;

  public GsonMessageFactoryImpl(Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T> String convertSocketMessage(SocketMessage<T> socketMessage) {
    return gson.toJson(socketMessage);
  }
}
