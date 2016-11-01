package io.infectnet.server.controller.websocket;

import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.service.user.UserDTO;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Optional;

public interface SessionAuthenticator {
    void authenticate(Session session, SocketMessage socketMessage) throws AuthenticationFailedException, MalformedMessageException;

    Optional<UserDTO> verifyAuthentication(Session session);
}
