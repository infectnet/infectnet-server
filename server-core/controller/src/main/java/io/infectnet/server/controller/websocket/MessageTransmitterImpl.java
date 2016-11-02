package io.infectnet.server.controller.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MessageTransmitterImpl implements MessageTransmitter {
    @Override
    public void transmitString(Session session, String message) throws IOException {
        session.getRemote().sendString(message);
    }

    @Override
    public void transmitBytes(Session session, ByteBuffer bytes) throws IOException {
        session.getRemote().sendBytes(bytes);
    }
}
