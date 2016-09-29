package io.infectnet.server.service.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.service.TokenDTO;
import io.infectnet.server.service.TokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

public class TokenServiceImpl implements TokenService {

    private static final long EXPIRE_MINUTES = 10;

    private static final int TOKEN_LENGTH = 16;

    private ModelMapper modelMapper;

    @Inject
    private TokenStorage tokenStorage;

    public TokenServiceImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public TokenDTO createNewToken() {
        TokenDTO newToken = new TokenDTO(RandomStringUtils.random(TOKEN_LENGTH), getCurrentExpireDate());

        Token token = modelMapper.map(newToken, Token.class);

        tokenStorage.saveToken(token);

        return newToken;
    }

    @Override
    public boolean exists(TokenDTO token) {
        return tokenStorage.getAllTokens().contains(modelMapper.map(token, Token.class));
    }

    @Override
    public void delete(TokenDTO token) {
        tokenStorage.deleteToken(modelMapper.map(token, Token.class));
    }

    @Override
    public List<TokenDTO> getAllTokens() {
        return tokenStorage.getAllTokens().stream()
                .map(t -> modelMapper.map(t, TokenDTO.class))
                .collect(Collectors.toList());
    }

    private LocalDateTime getCurrentExpireDate() {
        return LocalDateTime.now().with(temporal -> temporal.plus(EXPIRE_MINUTES, MINUTES));
    }

}
