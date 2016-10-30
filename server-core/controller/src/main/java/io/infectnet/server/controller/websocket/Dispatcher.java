package io.infectnet.server.controller.websocket;

import io.infectnet.server.controller.websocket.handler.OnCloseHandler;
import io.infectnet.server.controller.websocket.handler.OnConnectHandler;
import io.infectnet.server.controller.websocket.handler.OnMessageHandler;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class Dispatcher {

    @OnWebSocketConnect
    public void registerOnConnect(OnConnectHandler handler) {
        /**
         * Placeholder method.
         */
        System.out.println("Someone connected!");
    }

    @OnWebSocketClose
    public void registerOnClose(OnCloseHandler handler) {
        /**
         * Placeholder method.
         */
    }

    @OnWebSocketMessage
    public void registerOnMessage(Action action, OnMessageHandler handler) {
        /**
         * Placeholder method.
         */
    }
}
