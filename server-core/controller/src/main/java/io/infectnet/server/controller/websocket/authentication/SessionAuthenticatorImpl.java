package io.infectnet.server.controller.websocket.authentication;

import io.infectnet.server.controller.websocket.exception.AuthenticationFailedException;
import io.infectnet.server.service.user.UserDTO;
import io.infectnet.server.service.user.UserService;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionAuthenticatorImpl implements SessionAuthenticator {

  private Map<UserDTO, Session> sessionMap;

  private final UserService userService;

  public SessionAuthenticatorImpl(UserService userService) {
    sessionMap = new ConcurrentHashMap<>();
    this.userService = userService;
  }

  @Override
  public void authenticate(Session session, String username, String password)
      throws AuthenticationFailedException {
    Objects.requireNonNull(session);

    Optional<UserDTO> userOpt = userService.login(
        Objects.requireNonNull(username),
        Objects.requireNonNull(password));

    if (userOpt.isPresent()) {
      UserDTO user = userOpt.get();
      sessionMap.put(user, session);
    } else {
      throw new AuthenticationFailedException(username);
    }
  }

  @Override
  public Optional<UserDTO> verifyAuthentication(Session session) {
    Objects.requireNonNull(session);

    for (Map.Entry<UserDTO, Session> entry : sessionMap.entrySet()) {
      if (entry.getValue().equals(session)) {
        return Optional.of(entry.getKey());
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<Session> getSessionForUserDto(UserDTO userDTO) {
    return Optional.ofNullable(sessionMap.get(userDTO));
  }
}
