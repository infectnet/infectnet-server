package io.infectnet.server.controller.websocket.authentication;

import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.service.user.UserDTO;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Optional;

public interface SessionAuthenticator {

  /**
   * Authenticates the given session with the given credentials.
   * @param session the session to authenticate
   * @param username the username
   * @param password the password
   * @throws AuthenticationFailedException if the given credentials are invalid
   */
  void authenticate(Session session, String username, String password)
      throws AuthenticationFailedException;

  /**
   * Verifies, if the given session is already authenticated.
   * @param session the session to verify
   * @return an {@code Optional} of the authenticated user
   */
  Optional<UserDTO> verifyAuthentication(Session session);

  /**
   * Returns the session of the given authenticated user.
   * @param userDTO the authenticated user
   * @return an {@code Optional} of the session
   */
  Optional<Session> getSessionForUserDto(UserDTO userDTO);

}
