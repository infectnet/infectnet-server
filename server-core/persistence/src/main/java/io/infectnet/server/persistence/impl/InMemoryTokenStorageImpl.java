package io.infectnet.server.persistence.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;

import java.util.List;

public class InMemoryTokenStorageImpl implements TokenStorage {
    @Override
    public List<Token> getAllTokens() {
        return null;
    }

    @Override
    public Token getTokenById(Integer id) {
        return null;
    }

    @Override
    public void saveToken(Token token) {

    }

    @Override
    public void deleteToken(Token token) {

    }
}
