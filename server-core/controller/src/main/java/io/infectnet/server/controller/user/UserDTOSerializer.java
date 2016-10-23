package io.infectnet.server.controller.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import io.infectnet.server.service.user.UserDTO;

import java.lang.reflect.Type;

/**
 * Custom serializer that excludes the password field when serializing {@code UserDTO} objects.
 */
public class UserDTOSerializer implements JsonSerializer<UserDTO> {
  @Override
  public JsonElement serialize(UserDTO src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject obj = new JsonObject();

    obj.addProperty("username", src.getUserName());
    obj.addProperty("email", src.getEmail());
    obj.add("registrationDate", context.serialize(src.getRegistrationDate()));

    return obj;
  }
}
