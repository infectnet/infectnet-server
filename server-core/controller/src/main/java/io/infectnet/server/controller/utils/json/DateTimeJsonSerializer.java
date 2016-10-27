package io.infectnet.server.controller.utils.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeJsonSerializer
    implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

  private final DateTimeFormatter dateTimeFormatter;

  public DateTimeJsonSerializer() {
    this(DateTimeFormatter.ISO_DATE_TIME);
  }

  public DateTimeJsonSerializer(DateTimeFormatter dateTimeFormatter) {
    this.dateTimeFormatter = dateTimeFormatter;
  }

  @Override
  public LocalDateTime deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
    return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), dateTimeFormatter);
  }

  @Override
  public JsonElement serialize(LocalDateTime src, Type typeOfSrc,
                               JsonSerializationContext context) {
    return new JsonPrimitive(src.format(dateTimeFormatter));
  }
}
