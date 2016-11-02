package io.infectnet.server.controller.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonParser;
import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.admin.AuthenticationController;
import io.infectnet.server.controller.exception.ExceptionMapperController;
import io.infectnet.server.controller.info.InfoController;
import io.infectnet.server.controller.token.TokenController;
import io.infectnet.server.controller.user.RegistrationController;
import io.infectnet.server.controller.user.RegistrationDetails;
import io.infectnet.server.controller.user.UserDTOSerializer;
import io.infectnet.server.controller.user.UserListingController;
import io.infectnet.server.controller.utils.json.DateTimeJsonSerializer;
import io.infectnet.server.controller.websocket.Dispatcher;
import io.infectnet.server.controller.websocket.SessionAuthenticator;
import io.infectnet.server.controller.websocket.SessionAuthenticatorImpl;
import io.infectnet.server.service.admin.AuthenticationService;
import io.infectnet.server.service.configuration.ServiceModule;
import io.infectnet.server.service.token.TokenService;
import io.infectnet.server.service.user.UserDTO;
import io.infectnet.server.service.user.UserService;

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
    GsonBuilder gsonBuilder = new GsonBuilder()
        .setPrettyPrinting();

    gsonBuilder = setupTypeAdapters(gsonBuilder);

    return gsonBuilder.create();
  }

  @Provides
  @Singleton
  public static ExceptionMapperController providesExceptionMapperController(Gson gson) {
    return new ExceptionMapperController(gson);
  }

  @Provides
  @IntoSet
  @Singleton
  public static RestController providesTokenController(TokenService tokenService, Gson gson) {
    return new TokenController(tokenService, gson);
  }

  @Provides
  @IntoSet
  @Singleton
  public static RestController providesRegistrationController(UserService userService, Gson gson) {
    return new RegistrationController(userService, gson);
  }

  @Provides
  @IntoSet
  @Singleton
  public static RestController providesUserListingController(UserService userService, Gson gson) {
    return new UserListingController(userService, gson);
  }

  @Provides
  @IntoSet
  @Singleton
  public static RestController providesAuthenticationController(
      AuthenticationService authenticationService, Gson gson) {
    return new AuthenticationController(authenticationService, gson);
  }

  @Provides
  @Singleton
  public static JsonParser providesJsonParser(){
    return new JsonParser();
  }

  @Provides
  @Singleton
  public static SessionAuthenticator providesSessionAuthenticator(UserService userService, JsonParser jsonParser){
    return new SessionAuthenticatorImpl(userService, jsonParser);
  }

  @Provides
  @Singleton
  public static Dispatcher providesDispatcher(JsonParser jsonParser, SessionAuthenticator sessionAuthenticator){
    return new Dispatcher(jsonParser, sessionAuthenticator);
  }

  @Provides
  @IntoSet
  @Singleton
  public static RestController providesInfoController(Gson gson) {
    return new InfoController(gson);
  }

  private static GsonBuilder setupTypeAdapters(GsonBuilder gsonBuilder) {
    gsonBuilder
        .registerTypeAdapter(LocalDateTime.class, new DateTimeJsonSerializer())
        .registerTypeAdapter(RegistrationDetails.class, new RegistrationDetails.Deserializer())
        .registerTypeAdapter(UserDTO.class, new UserDTOSerializer());

    return gsonBuilder;
  }

}
