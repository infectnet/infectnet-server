package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.controller.websocket.handler.OnCloseHandler;
import io.infectnet.server.controller.websocket.handler.OnConnectHandler;
import io.infectnet.server.controller.websocket.handler.OnMessageHandler;
import io.infectnet.server.service.user.UserDTO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.*;

@WebSocket
public class Dispatcher {

    private final List<OnConnectHandler> onConnectHandlers;

    private final List<OnCloseHandler> onCloseHandlers;

    private final Map<Action, OnMessageHandler> onMessageHandlerMap;

    private final JsonParser jsonParser;

    private final SessionAuthenticator sessionAuthenticator;

    public Dispatcher(JsonParser jsonParser, SessionAuthenticator sessionAuthenticator) {
        this.onConnectHandlers = new ArrayList<>();
        this.onCloseHandlers = new ArrayList<>();
        this.onMessageHandlerMap = new EnumMap<>(Action.class);
        this.jsonParser = jsonParser;
        this.sessionAuthenticator = sessionAuthenticator;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        for(OnConnectHandler handler : onConnectHandlers){
            handler.handle(session);
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        for(OnCloseHandler handler : onCloseHandlers){
            handler.handle(session, statusCode, reason);
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try{
            SocketMessage socketMessage = getMessage(message);
            Action action = socketMessage.getAction();
            if(action == Action.AUTH){
                sessionAuthenticator.authenticate(session, socketMessage);
            }else {
                OnMessageHandler handler = onMessageHandlerMap.get(action);
                Optional<UserDTO> user = sessionAuthenticator.verifyAuthentication(session);
                if(user.isPresent()){
                    handler.handle(user.get(), socketMessage);
                }
            }
        }catch (MalformedMessageException e){
            //TODO exception handle
        } catch (AuthenticationFailedException e) {
            //TODO exception handle
        }
    }

    public void registerOnConnect(OnConnectHandler handler) {
        onConnectHandlers.add(Objects.requireNonNull(handler));
    }

    public void registerOnClose(OnCloseHandler handler) {
        onCloseHandlers.add(Objects.requireNonNull(handler));
    }

    public void registerOnMessage(Action action, OnMessageHandler handler) {
        onMessageHandlerMap.put(Objects.requireNonNull(action), Objects.requireNonNull(handler));
    }

    private SocketMessage getMessage(String message) throws MalformedMessageException{
        try {
            JsonElement jsonElement = jsonParser.parse(message);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String actionStr = jsonObject.get("action").getAsString();
            String arguments = jsonObject.get("arguments").toString();
            Action action = Action.valueOf(actionStr);
            return new SocketMessage(action, arguments);
        }catch (Exception e){
            throw new MalformedMessageException(e);
        }
    }
}
