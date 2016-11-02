package io.infectnet.server.controller.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface MessageTransmitter {
    void transmitString(Session session, String message) throws IOException;

    void transmitBytes(Session session, ByteBuffer bytes) throws IOException;
}
