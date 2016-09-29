package io.infectnet.server.service;

import java.util.List;
import java.util.Optional;

/**
 * Interface for services handling token generation.
 */
public interface TokenService {

    /**
     * Creates a new unique token.
     *
     * @return the new token
     */
    TokenDTO createNewToken();

    /**
     * Checks if the token exists in storage.
     *
     * @param token the token to check
     * @return true if the token exists, false otherwise
     */
    boolean exists(TokenDTO token);

    /**
     * Deletes a {@link TokenDTO} from the storage.
     *
     * @param token the token to delete
     */
    void delete(TokenDTO token);

    /**
     * Returns all currently valid tokens.
     *
     * @return a list of tokens
     */
    List<TokenDTO> getAllTokens();

    /**
     * Returns a {@link TokenDTO} with the given token string.
     *
     * @return an {@link Optional} containing the token
     */
    TokenDTO getTokenByTokenString(String token);

}
