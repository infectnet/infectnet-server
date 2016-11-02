package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import org.eclipse.jetty.websocket.api.Session;

public class AuthenticationController {

    private final SessionAuthenticator sessionAuthenticator;

    private final JsonParser jsonParser;

    public AuthenticationController(SessionAuthenticator sessionAuthenticator, JsonParser jsonParser) {
        this.sessionAuthenticator = sessionAuthenticator;
        this.jsonParser = jsonParser;
    }

    public void handleAuthentication(Session session, SocketMessage socketMessage) throws MalformedMessageException {
        String username = null;
        String password = null;
        try {
            JsonElement jsonElement = jsonParser.parse(socketMessage.getArguments());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            username = jsonObject.get("username").getAsString();
            password = jsonObject.get("password").getAsString();
        }catch (JsonParseException e){
            throw new MalformedMessageException(e);
        }

        try {
            sessionAuthenticator.authenticate(session, username, password);
        } catch (AuthenticationFailedException e) {
            //TODO send back error
        }
    }
}
