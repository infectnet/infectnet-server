package io.infectnet.server.controller.websocket;

/**
 * An enum for the different type of actions the User can make, which will go through the WebSocket,
 * and the {@link Dispatcher}
 */
public enum Action {
    AUTH,
    SUBSCRIBE,
    NEW_CODE;
}
