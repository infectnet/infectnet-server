package io.infectnet.server.service.admin;

import java.util.Optional;

public interface AuthenticationService {
  Optional<String> login(String username, String password);

  boolean isAuthenticated(String token);

  Optional<String> renewToken(String token);
}
