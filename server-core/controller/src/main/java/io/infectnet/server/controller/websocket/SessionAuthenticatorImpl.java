package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
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
        sessionMap = new ConcurrentHashMap<>();
        this.userService = userService;
        this.jsonParser = jsonParser;
    }

    public void authenticate(Session session, SocketMessage socketMessage) throws AuthenticationFailedException, MalformedMessageException {
        String username = null;
        String password = null;
        try {
            JsonElement jsonElement = jsonParser.parse(socketMessage.getArguments());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            username = jsonObject.get("username").getAsString();
            password = jsonObject.get("password").getAsString();
        }catch (Exception e) {
            throw new MalformedMessageException(e);
        }
        try{
            Optional<UserDTO> userOpt = userService.login(username, password);
            if(userOpt.isPresent()){
                UserDTO user = userOpt.get();
                sessionMap.put(user,session);
            }
        }catch (Exception e){
            throw new AuthenticationFailedException(username,e);
        }
    }

    public Optional<UserDTO> verifyAuthentication(Session session){
            for(Map.Entry<UserDTO, Session> entry: sessionMap.entrySet()){
                if(entry.getValue().equals(session)){
                    return Optional.of(entry.getKey());
                }
            }
            return Optional.empty();
    }
}
