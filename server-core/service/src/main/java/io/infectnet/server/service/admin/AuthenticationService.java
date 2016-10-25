package io.infectnet.server.service.admin;

import java.util.Optional;

/**
 * Service interface that must be implemented by classes that provide authentication methods
 * for administrators.
 */
public interface AuthenticationService {
  /**
   * Matches the passed credentials with the stored administrator username and password.
   * @param username the administrator username
   * @param password the administrator password
   * @return an {@code Optional} containing a token if the login succeeds, an empty {@code Optional} otherwise
   */
  Optional<String> login(String username, String password);

  /**
   * Checks whether the token can be used to make requests to admin endpoints.
   * @param token the token to be examined
   * @return {@code true} if the token is valid, {@code false} otherwise
   */
  boolean isAuthenticated(String token);

  /**
   * Renews the token without having to sign in. Can only be used with a valid, non-expired token.
   * @param token the token to be renewed
   * @return the renewed token (can be the same or a completely new one)
   */
  Optional<String> renewToken(String token);
}
