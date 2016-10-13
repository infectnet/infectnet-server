package io.infectnet.server.service;

import java.util.List;
import java.util.Optional;

/**
 * Interface for services handling token generation.
 */
public interface TokenService {

  /**
   * Creates a new unique token.
   * @return the new token
   */
  TokenDTO createNewToken();

  /**
   * Checks if the token is valid and is in the storage.
   * @param token the token to check
   * @return true if the token is valid, false otherwise
   */
  boolean exists(TokenDTO token);

  /**
   * Deletes a token from the storage.
   * @param token the token to delete
   */
  void delete(TokenDTO token);

  /**
   * Returns all currently valid tokens.
   * @return a list of tokens
   */
  List<TokenDTO> getAllTokens();

  /**
   * Returns a token with the given token string if it exists.
   * @param tokenString the token string to search by
   * @return an {@link Optional} containing the token
   */
  Optional<TokenDTO> getTokenByTokenString(String tokenString);

}
