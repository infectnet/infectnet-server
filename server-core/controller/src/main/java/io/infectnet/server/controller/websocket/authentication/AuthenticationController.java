package io.infectnet.server.controller.websocket.authentication;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import io.infectnet.server.controller.websocket.messaging.MessageFactory;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class AuthenticationController {

    private final SessionAuthenticator sessionAuthenticator;

    private final JsonParser jsonParser;

    private final MessageTransmitter messageTransmitter;

    private final MessageFactory messageFactory;

    public AuthenticationController(SessionAuthenticator sessionAuthenticator,
                                    JsonParser jsonParser, MessageTransmitter messageTransmitter,
                                    MessageFactory messageFactory) {
        this.sessionAuthenticator = sessionAuthenticator;
        this.jsonParser = jsonParser;
        this.messageTransmitter = messageTransmitter;
        this.messageFactory = messageFactory;
    }

    public void handleAuthentication(Session session, SocketMessage socketMessage) throws MalformedMessageException, IOException {
        Credentials credentials;

        try {
            credentials = parseMessage(socketMessage);
        }catch (JsonParseException e){
            throw new MalformedMessageException(e);
        }

        try {
            sessionAuthenticator.authenticate(session, credentials.username, credentials.password);
        } catch (AuthenticationFailedException e) {
           String message = messageFactory.convertError(e);

           messageTransmitter.transmitString(session, message);
        }
    }

    private Credentials parseMessage(SocketMessage socketMessage) {
        JsonElement jsonElement = jsonParser.parse(socketMessage.getArguments());
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        return new Credentials(username, password);
    }

    private static class Credentials {
        private final String username;

        private final String password;

        private Credentials(String username, String password) {
            this.username = username;

            this.password = password;
        }
    }
}
