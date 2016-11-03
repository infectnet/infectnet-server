package io.infectnet.server.controller.websocket.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import io.infectnet.server.controller.websocket.messaging.Action;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class AuthenticationController {

    private final SessionAuthenticator sessionAuthenticator;

    private final Gson gson;

    private final MessageTransmitter messageTransmitter;

    public AuthenticationController(SessionAuthenticator sessionAuthenticator,
                                    Gson gson, MessageTransmitter messageTransmitter) {
        this.sessionAuthenticator = sessionAuthenticator;
        this.gson = gson;
        this.messageTransmitter = messageTransmitter;
    }

    public void handleAuthentication(Session session, String arguments) throws MalformedMessageException, IOException {
        Credentials credentials;

        try {
            credentials = gson.fromJson(arguments, Credentials.class);
        }catch (JsonParseException e){
            throw new MalformedMessageException(e);
        }

        try {
            authenticate(session, credentials);
        } catch (AuthenticationFailedException e) {
           messageTransmitter.transmitException(session, e);
        }
    }

    private void authenticate(Session session, Credentials credentials)
        throws AuthenticationFailedException, IOException {
        sessionAuthenticator.authenticate(session, credentials.username, credentials.password);

        SocketMessage<String> message =
            new SocketMessage<>(Action.OK, StringUtils.EMPTY, String.class);

        messageTransmitter.transmitString(session, message);
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
