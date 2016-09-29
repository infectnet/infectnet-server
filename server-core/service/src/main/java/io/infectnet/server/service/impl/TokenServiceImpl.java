package io.infectnet.server.service.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.service.TokenDTO;
import io.infectnet.server.service.TokenService;
import org.modelmapper.ModelMapper;

import java.util.List;

public class TokenServiceImpl implements TokenService {

    private ModelMapper modelMapper;

    public TokenServiceImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public TokenDTO createNewToken() {
        // To be implemented...
        TokenDTO newToken = new TokenDTO();
        Token token = modelMapper.map(newToken, Token.class);

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
