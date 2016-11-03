package io.infectnet.server.controller.websocket.messaging;

import io.infectnet.server.controller.error.ErrorConvertibleException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public interface MessageTransmitter {
    <T> void transmitString(Session session, SocketMessage<T> socketMessage) throws IOException;

    void transmitException(Session session, ErrorConvertibleException exception) throws IOException;
}
