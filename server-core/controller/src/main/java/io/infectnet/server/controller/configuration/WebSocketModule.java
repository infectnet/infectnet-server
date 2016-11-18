package io.infectnet.server.controller.configuration;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import io.infectnet.server.controller.engine.EngineConnector;
import io.infectnet.server.controller.websocket.Dispatcher;
import io.infectnet.server.controller.websocket.authentication.AuthenticationController;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticatorImpl;
import io.infectnet.server.controller.websocket.game.GameController;
import io.infectnet.server.controller.websocket.messaging.Action;
import io.infectnet.server.controller.websocket.messaging.GsonMessageFactoryImpl;
import io.infectnet.server.controller.websocket.messaging.MessageFactory;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitterImpl;
import io.infectnet.server.service.user.UserService;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class WebSocketModule {
  @Provides
  @Singleton
  public static JsonParser providesJsonParser() {
    return new JsonParser();
  }

  @Provides
  @Singleton
  public static SessionAuthenticator providesSessionAuthenticator(UserService userService) {
    return new SessionAuthenticatorImpl(userService);
  }

  @Provides
  @Singleton
  public static AuthenticationController providesAuthenticationController(
      SessionAuthenticator sessionAuthenticator, Gson gson, MessageTransmitter messageTransmitter) {
    return new AuthenticationController(sessionAuthenticator, gson, messageTransmitter);
  }

  @Provides
  @Singleton
  public static GameController providesGameController(EngineConnector engineConnector, Gson gson,
                                                      SessionAuthenticator sessionAuthenticator,
                                                      MessageTransmitter messageTransmitter) {
    return new GameController(engineConnector, gson, sessionAuthenticator, messageTransmitter);
  }

  @Provides
  @Singleton
  public static MessageFactory providesMessageFactory(Gson gson) {
    return new GsonMessageFactoryImpl(gson);
  }

  @Provides
  @Singleton
  public static MessageTransmitter providesMessageTransmitter(MessageFactory messageFactory) {
    return new MessageTransmitterImpl(messageFactory);
  }

  @Provides
  @Singleton
  public static Dispatcher providesDispatcher(JsonParser jsonParser,
                                              MessageTransmitter messageTransmitter,
                                              AuthenticationController authenticationController,
                                              GameController gameController) {
    Dispatcher dispatcher = new Dispatcher(jsonParser, messageTransmitter);
    dispatcher.registerOnMessage(Action.AUTH, authenticationController::handleAuthentication);
    dispatcher.registerOnMessage(Action.NEW_CODE, gameController::handleNewCodeUpload);
    return dispatcher;
  }

}
