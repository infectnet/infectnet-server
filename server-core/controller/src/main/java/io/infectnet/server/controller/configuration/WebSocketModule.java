package io.infectnet.server.controller.configuration;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import dagger.Module;
import dagger.Provides;
import io.infectnet.server.controller.websocket.*;
import io.infectnet.server.controller.websocket.authentication.AuthenticationController;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticatorImpl;
import io.infectnet.server.controller.websocket.messaging.MessageFactory;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitterImpl;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import io.infectnet.server.service.user.UserService;

import javax.inject.Singleton;

@Module
public class WebSocketModule {
    @Provides
    @Singleton
    public static JsonParser providesJsonParser(){
        return new JsonParser();
    }

    @Provides
    @Singleton
    public static SessionAuthenticator providesSessionAuthenticator(UserService userService){
        return new SessionAuthenticatorImpl(userService);
    }

    @Provides
    @Singleton
    public static MessageFactory providesMessageFactory(Gson gson) {
        return new MessageFactory(gson);
    }

    @Provides
    @Singleton
    public static AuthenticationController providesAuthenticationController(SessionAuthenticator sessionAuthenticator, JsonParser jsonParser, MessageTransmitter messageTransmitter, MessageFactory messageFactory){
        return new AuthenticationController(sessionAuthenticator,jsonParser, messageTransmitter,
            messageFactory);
    }

    @Provides
    @Singleton
    public static MessageTransmitter providesMessageTransmitter(){
        return new MessageTransmitterImpl();
    }

    @Provides
    @Singleton
    public static Dispatcher providesDispatcher(JsonParser jsonParser, AuthenticationController authenticationController){
        Dispatcher dispatcher = new Dispatcher(jsonParser);
        dispatcher.registerOnMessage(SocketMessage.Action.AUTH, authenticationController::handleAuthentication);
        return dispatcher;
    }

}
