package io.infectnet.server.persistence.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TokenStorage} in-memory implementation.
 */
public class InMemoryTokenStorageImpl implements TokenStorage {


    private List<Token> tokens = new ArrayList<>();

    @Override
    public List<Token> getAllTokens() {
        return new ArrayList<>(tokens);
    }

    @Override
    public void saveToken(Token token) {
        tokens.add(token);
    }

    @Override
    public void deleteToken(Token token) {
        tokens.remove(token);
    }
}
