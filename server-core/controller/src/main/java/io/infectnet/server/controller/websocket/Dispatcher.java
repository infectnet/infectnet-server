package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.infectnet.server.controller.exception.MalformedMessageException;
import io.infectnet.server.controller.websocket.handler.OnCloseHandler;
import io.infectnet.server.controller.websocket.handler.OnConnectHandler;
import io.infectnet.server.controller.websocket.handler.OnMessageHandler;
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

    private final Map<Action, OnMessageHandler> onMessageHandlerHashMap;

    private final JsonParser jsonParser;

    public Dispatcher(JsonParser jsonParser) {
        this.onConnectHandlers = new ArrayList();
        this.onCloseHandlers = new ArrayList();
        this.onMessageHandlerHashMap = new EnumMap(Action.class);
        this.jsonParser = jsonParser;
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
            Message msg = getMessage(message);
            OnMessageHandler handler = onMessageHandlerHashMap.get(msg.action);
            handler.handle(session, msg.arguments);
        }catch (MalformedMessageException e){
            //TODO
        }
    }

    public void registerOnConnect(OnConnectHandler handler) {
        onConnectHandlers.add(Objects.requireNonNull(handler));
    }

    public void registerOnClose(OnCloseHandler handler) {
        onCloseHandlers.add(Objects.requireNonNull(handler));
    }

    public void registerOnMessage(Action action, OnMessageHandler handler) {
        onMessageHandlerHashMap.put(Objects.requireNonNull(action), Objects.requireNonNull(handler));
    }

    private Message getMessage(String message) throws MalformedMessageException{
        try {
            JsonElement jsonElement = jsonParser.parse(message);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String actionStr = jsonObject.get("action").getAsString();
            String arguments = jsonObject.get("arguments").getAsString();
            Action action = Action.valueOf(actionStr);
            return new Message(action, arguments);
        }catch (Exception e){
            throw new MalformedMessageException(message, e);
        }
    }

    private class Message{

        private final Action action;

        private final String arguments;

        public Message(Action action, String arguments) {
            this.action = action;
            this.arguments = arguments;
        }
    }
}
