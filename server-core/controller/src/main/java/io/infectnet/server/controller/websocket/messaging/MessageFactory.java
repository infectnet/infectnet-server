package io.infectnet.server.controller.websocket.messaging;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.infectnet.server.controller.error.ErrorConvertibleException;
import io.infectnet.server.controller.websocket.messaging.SocketMessage.Action;

public class MessageFactory {
  private final Gson gson;

  public MessageFactory(Gson gson) {
    this.gson = gson;
  }

  public final String convertObject(Action action, Object argumentsObj) {
    JsonObject obj = new JsonObject();

    obj.addProperty("action", action.toString());

    obj.add("arguments", gson.toJsonTree(argumentsObj));

    return obj.toString();
  }

  public String convertError(ErrorConvertibleException exception){
    return convertObject(Action.AUTH, exception.toError());
  }
}
