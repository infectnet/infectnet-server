package io.infectnet.server.controller.websocket;

import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.service.user.UserDTO;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Optional;

public interface SessionAuthenticator {
    void authenticate(Session session, String username, String password) throws AuthenticationFailedException;

    Optional<UserDTO> verifyAuthentication(Session session);
}
