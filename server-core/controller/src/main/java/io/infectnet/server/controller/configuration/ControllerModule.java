package io.infectnet.server.controller.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.token.TokenController;
import io.infectnet.server.controller.utils.json.DateTimeJsonSerializer;
import io.infectnet.server.service.configuration.ServiceModule;
import io.infectnet.server.service.token.TokenService;

import java.time.LocalDateTime;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module(includes = ServiceModule.class)
public class ControllerModule {

  @Provides
  @Singleton
  public static Gson providesGson() {
    GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
    return setupGsonBuilder(gsonBuilder).create();
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

  private static GsonBuilder setupGsonBuilder(GsonBuilder gsonBuilder) {
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new DateTimeJsonSerializer());
    return gsonBuilder;
  }


}
