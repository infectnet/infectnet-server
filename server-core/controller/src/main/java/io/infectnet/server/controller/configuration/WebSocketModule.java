package io.infectnet.server.controller.configuration;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import io.infectnet.server.controller.engine.EngineConnector;
import io.infectnet.server.controller.websocket.WebSocketController;
import io.infectnet.server.controller.websocket.WebSocketDispatcher;
import io.infectnet.server.controller.websocket.authentication.AuthenticationController;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticatorImpl;
import io.infectnet.server.controller.websocket.code.CodeController;
import io.infectnet.server.controller.websocket.messaging.GsonMessageFactoryImpl;
import io.infectnet.server.controller.websocket.messaging.MessageFactory;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitterImpl;
import io.infectnet.server.controller.websocket.status.WebSocketStatusTransmitter;
import io.infectnet.server.controller.websocket.subscribe.SubscriptionController;
import io.infectnet.server.engine.core.status.StatusConsumer;
import io.infectnet.server.service.user.UserService;

import java.util.Set;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

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
  @IntoSet
  @Singleton
  public static WebSocketController providesAuthenticationController(
      SessionAuthenticator sessionAuthenticator, Gson gson, MessageTransmitter messageTransmitter) {
    return new AuthenticationController(sessionAuthenticator, gson, messageTransmitter);
  }

  @Provides
  @IntoSet
  @Singleton
  public static WebSocketController providesCodeController(EngineConnector engineConnector,
                                                           Gson gson,
                                                           SessionAuthenticator sessionAuthenticator,
                                                           MessageTransmitter messageTransmitter) {
    return new CodeController(engineConnector, gson, sessionAuthenticator, messageTransmitter);
  }

  @Provides
  @IntoSet
  @Singleton
  public static WebSocketController providesSubscriptionController(EngineConnector engineConnector,
                                                                   SessionAuthenticator sessionAuthenticator,
                                                                   MessageTransmitter messageTransmitter) {
    return new SubscriptionController(engineConnector, sessionAuthenticator, messageTransmitter);
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
  public static WebSocketDispatcher providesWebSocketDispatcher(
      Set<WebSocketController> webSocketControllers,
      JsonParser jsonParser,
      MessageTransmitter messageTransmitter) {

    return new WebSocketDispatcher(webSocketControllers, jsonParser, messageTransmitter);
  }

  @Provides
  @Singleton
  public static StatusConsumer providesStatusConsumer(SessionAuthenticator sessionAuthenticator,
                                                      MessageTransmitter messageTransmitter,
                                                      UserService userService) {
    WebSocketStatusTransmitter transmitter = new WebSocketStatusTransmitter(
        messageTransmitter, sessionAuthenticator, userService);

    return transmitter::transmit;
  }


}
