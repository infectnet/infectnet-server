package io.infectnet.server.controller.websocket.messaging;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static io.infectnet.server.controller.websocket.messaging.Action.OK;

import java.lang.reflect.Type;

public class SocketMessage<T> {
  public static final SocketMessage<String> EMPTY_OK = new SocketMessage<>(OK, EMPTY, String.class);

  private final Action action;

  private final T arguments;

  private final Class<T> argumentClass;

  public SocketMessage(Action action, T arguments, Class<T> argumentClass) {
    this.action = action;
    this.arguments = arguments;
    this.argumentClass = argumentClass;
  }

  public Action getAction() {
    return action;
  }

  public T getArguments() {
    return arguments;
  }

  public Class<T> getArgumentClass() {
    return argumentClass;
  }

  public static class Serializer implements JsonSerializer<SocketMessage> {
    @Override
    public JsonElement serialize(SocketMessage src, Type typeOfSrc,
                                 JsonSerializationContext context) {
      JsonObject obj = new JsonObject();

      obj.addProperty("action", src.action.toString());

      @SuppressWarnings("unchecked")
      JsonElement arguments =
          context.serialize(src.arguments, TypeToken.get(src.argumentClass).getType());

      obj.add("arguments", arguments);

      return obj;
    }
  }
}
