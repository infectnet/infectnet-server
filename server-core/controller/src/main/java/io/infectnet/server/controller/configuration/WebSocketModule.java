package io.infectnet.server.controller.configuration;

import com.google.gson.JsonParser;
import dagger.Module;
import dagger.Provides;
import io.infectnet.server.controller.websocket.*;
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
    public static AuthenticationController providesAuthenticationController(SessionAuthenticator sessionAuthenticator, JsonParser jsonParser){
        return new AuthenticationController(sessionAuthenticator,jsonParser);
    }

    @Provides
    @Singleton
    public static Dispatcher providesDispatcher(JsonParser jsonParser, SessionAuthenticator sessionAuthenticator, AuthenticationController authenticationController){
        Dispatcher dispatcher = new Dispatcher(jsonParser, sessionAuthenticator);
        dispatcher.registerOnMessage(Action.AUTH, authenticationController::handleAuthentication);
        return dispatcher;
    }

}
