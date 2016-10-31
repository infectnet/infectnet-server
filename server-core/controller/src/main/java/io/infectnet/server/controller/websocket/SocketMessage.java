package io.infectnet.server.controller.websocket;

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
}
