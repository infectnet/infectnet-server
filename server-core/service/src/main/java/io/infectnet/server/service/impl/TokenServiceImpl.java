package io.infectnet.server.service.impl;

import io.infectnet.server.service.TokenDTO;
import io.infectnet.server.service.TokenService;

import java.util.List;

public class TokenServiceImpl implements TokenService {

    @Override
    public TokenDTO createNew() {
        return null;
    }

    @Override
    public boolean exists(TokenDTO token) {
        return false;
    }

    @Override
    public void delete(TokenDTO token) {

    }

    @Override
    public List<TokenDTO> getAllTokens() {
        return null;
    }
}
