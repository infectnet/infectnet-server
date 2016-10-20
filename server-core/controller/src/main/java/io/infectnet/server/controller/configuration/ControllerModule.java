package io.infectnet.server.controller.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.token.TokenController;
import io.infectnet.server.service.configuration.ServiceModule;
import io.infectnet.server.service.token.TokenService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module(includes = ServiceModule.class)
public class ControllerModule {

  @Provides
  @Singleton
  public static Gson providesGson() {
    return new GsonBuilder()
        .setPrettyPrinting()

        .registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))

        .registerTypeAdapter(LocalDateTime.class,
            (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                new JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME)))

        .create();
  }

  @Provides
  @IntoSet
  @Singleton
  public static RestController providesTokenController(TokenService tokenService, Gson gson) {
    TokenController tokenController = new TokenController();
    tokenController.setTokenService(tokenService);
    tokenController.setGson(gson);
    return tokenController;
  }


}
