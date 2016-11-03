package io.infectnet.server.controller.websocket.messaging;

import io.infectnet.server.controller.error.ErrorConvertibleException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class MessageTransmitterImpl implements MessageTransmitter {
    private final MessageFactory messageFactory;

    public MessageTransmitterImpl(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public final <T> void transmitString(Session session, SocketMessage<T> socketMessage)
        throws IOException {
        String transmittableString = messageFactory.convertSocketMessage(socketMessage);

        session.getRemote().sendString(transmittableString);
    }

    @Override
    public void transmitException(Session session, ErrorConvertibleException exception)
        throws IOException {
        SocketMessage<io.infectnet.server.controller.error.Error> message =
            new SocketMessage<>(Action.ERROR, exception.toError(),
                                io.infectnet.server.controller.error.Error.class);

        transmitString(session, message);
    }
}
