package io.infectnet.server.service.impl;

import io.infectnet.server.service.Token;
import io.infectnet.server.service.TokenService;

import java.util.List;

public class TokenServiceImpl implements TokenService {

    @Override
    public Token createNew() {
        return null;
    }

    @Override
    public boolean exists(Token token) {
        return false;
    }

    @Override
    public void delete(Token token) {

    }

    @Override
    public List<Token> getAllTokens() {
        return null;
    }
}
