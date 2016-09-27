package io.infectnet.server.persistence.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;

import java.util.List;

public class InMemoryTokenStorageImpl implements TokenStorage {
    @Override
    public List<Token> getAllTokens() {
        // To be implemented...
        return null;
    }

    @Override
    public Token getTokenById(Integer id) {
        // To be implemented...
        return null;
    }

    @Override
    public void saveToken(Token token) {
        // To be implemented...
    }

    @Override
    public void deleteToken(Token token) {
        // To be implemented...
    }
}
