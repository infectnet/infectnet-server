package io.infectnet.server.controller.rest.user;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

/**
 * Value object containing the details posted in a registration attempt.
 */
public class RegistrationDetails {
  private final String username;

  private final String password;

  private final String token;

  private final String email;

  public RegistrationDetails(String username, String password, String token, String email) {
    this.username = username;
    this.password = password;
    this.token = token;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getToken() {
    return token;
  }

  public String getEmail() {
    return email;
  }

  public static class Deserializer implements JsonDeserializer<RegistrationDetails> {
    @Override
    public RegistrationDetails deserialize(JsonElement json, Type typeOfT,
                                           JsonDeserializationContext context)
        throws JsonParseException {
      JsonObject obj = json.getAsJsonObject();

      return new RegistrationDetails(orEmpty(obj, "username"), orEmpty(obj, "password"),
                                     orEmpty(obj, "token"), orEmpty(obj, "email"));
    }

    private String orEmpty(JsonObject obj, String member) {
     return obj.has(member) ? obj.get(member).getAsString() : StringUtils.EMPTY;
    }
  }
}
