package io.infectnet.server.persistence;

import java.util.List;

/**
 * Interface describing a container, which is used for handling {@link Token}s.
 */
public interface TokenStorage {

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
     * Deletes a {@link Token} from the storage.
     *
     * @param token the token to be deleted
     */
    void deleteToken(Token token);
}
