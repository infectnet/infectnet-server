package io.infectnet.server.controller.websocket.messaging;

import io.infectnet.server.controller.websocket.Dispatcher;

public class SocketMessage {

    private final Action action;

    private final String arguments;

    public SocketMessage(Action action, String arguments) {
        this.action = action;
        this.arguments = arguments;
    }

    public Action getAction() {
        return action;
    }

    public String getArguments() {
        return arguments;
    }

    /**
     * An enum for the different type of actions the User can make, which will go through the WebSocket,
     * and the {@link Dispatcher}
     */
    public enum Action {
        AUTH,
        SUBSCRIBE,
        NEW_CODE,
        ERROR
    }
}
