package io.infectnet.server.persistence;

import java.util.List;
import java.util.Optional;

/**
 * Interface describing a container, which is used for handling {@link Token}s.
 */
public interface TokenStorage {

    /**
     * Returns the {@link Token} with the given token string.
     *
     * @param token the actual token string
     * @return an {@link Optional} containing the token
     */
    Optional<Token> getTokenByTokenString(String token);

    /**
     * Returns all currently valid tokens.
     *
     * @return a list of tokens
     */
    List<Token> getAllTokens();

    /**
     * Persists a {@link Token} in the storage.
     *
     * @param token the token to be saved
     */
    void saveToken(Token token);

    /**
     * Checks if the given token is in the storage.
     *
     * @param token the token to search for
     * @return true is the token exists, false otherwise
     */
    boolean exists(Token token);

    /**
     * Deletes a {@link Token} from the storage.
     *
     * @param token the token to be deleted
     */
    void deleteToken(Token token);
}
