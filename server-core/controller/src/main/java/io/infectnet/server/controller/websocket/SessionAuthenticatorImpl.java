package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.infectnet.server.controller.exception.AuthenticationFailedException;
import io.infectnet.server.service.user.UserDTO;
import io.infectnet.server.service.user.UserService;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionAuthenticatorImpl implements SessionAuthenticator {

    private Map<UserDTO, Session> sessionMap;

    private final UserService userService;

    private final JsonParser jsonParser;

    public SessionAuthenticatorImpl(UserService userService, JsonParser jsonParser) {
        sessionMap = new ConcurrentHashMap();
        this.userService = userService;
        this.jsonParser = jsonParser;
    }

    void authenticate(Session session, SocketMessage socketMessage) throws AuthenticationFailedException {
        try{
            JsonElement jsonElement = jsonParser.parse(socketMessage.getArguments());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String username = jsonObject.get("username").getAsString();
            String password = jsonObject.get("password").getAsString();
            Optional<UserDTO> userOpt = userService.login(username, password);
            if(userOpt.isPresent()){
                UserDTO user = userOpt.get();
                sessionMap.put(user,session);
            }
        }catch (Exception e){
            throw new AuthenticationFailedException("Authentication failed",e);
        }
    }

    Optional<UserDTO> verifyAuthentication(Session session, SocketMessage socketMessage){
        return Optional.empty();
    }
}
