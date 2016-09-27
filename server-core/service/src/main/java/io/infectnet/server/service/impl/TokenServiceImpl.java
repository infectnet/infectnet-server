package io.infectnet.server.service.impl;

import io.infectnet.server.service.TokenDTO;
import io.infectnet.server.service.TokenService;

import java.util.List;

public class TokenServiceImpl implements TokenService {

    @Override
    public TokenDTO createNewToken() {
        // To be implemented...
        return null;
    }

    @Override
    public boolean exists(TokenDTO token) {
        // To be implemented...
        return false;
    }

    @Override
    public void delete(TokenDTO token) {
        // To be implemented...
    }

    @Override
    public List<TokenDTO> getAllTokens() {
        // To be implemented...
        return null;
    }
}
