package io.infectnet.server.controller.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.infectnet.server.controller.websocket.handler.OnCloseHandler;
import io.infectnet.server.controller.websocket.handler.OnConnectHandler;
import io.infectnet.server.controller.websocket.handler.OnMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@WebSocket
public class Dispatcher {

    private List<OnConnectHandler> onConnectHandlers;

    private List<OnCloseHandler> onCloseHandlers;

    private Map<Action, OnMessageHandler> onMessageHandlerHashMap;

    private JsonParser jsonParser;

    public Dispatcher() {
        this.onConnectHandlers = new ArrayList();
        this.onCloseHandlers = new ArrayList();
        this.onMessageHandlerHashMap = new EnumMap(Action.class);
        this.jsonParser = new JsonParser();
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
        JsonElement jsonElement = jsonParser.parse(message);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Action action = Action.valueOf(jsonObject.get("action").getAsString());
        String arguments = jsonObject.get("arguments").getAsString();

        OnMessageHandler handler = onMessageHandlerHashMap.get(action);
        handler.handle(session, arguments);
    }

    public void registerOnConnect(OnConnectHandler handler) {
        if(handler != null){
            onConnectHandlers.add(handler);
        }
    }

    public void registerOnClose(OnCloseHandler handler) {
        if(handler != null){
            onCloseHandlers.add(handler);
        }
    }

    public void registerOnMessage(Action action, OnMessageHandler handler) {
        if(handler != null){
            onMessageHandlerHashMap.put(action, handler);
        }
    }
}
