package io.infectnet.server.service.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.service.TokenDTO;
import io.infectnet.server.service.TokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Default {@link TokenService} implementation.
 */
public class TokenServiceImpl implements TokenService {

    private static final long EXPIRE_MINUTES = 10;

    private static final int TOKEN_LENGTH = 16;

    private final ModelMapper modelMapper;

    private final TokenStorage tokenStorage;

    @Inject
    public TokenServiceImpl(TokenStorage tokenStorage, ModelMapper modelMapper) {
        this.tokenStorage = tokenStorage;
        this.modelMapper = modelMapper;
    }

    @Override
    public TokenDTO createNewToken() {
        String tokenString = StringUtils.EMPTY;

        do {
            tokenString = RandomStringUtils.random(TOKEN_LENGTH);
        }
        while (tokenStorage.getTokenByTokenString(tokenString).isPresent());

        TokenDTO newToken = new TokenDTO(tokenString, getCurrentExpireDate());

        Token token = modelMapper.map(newToken, Token.class);

        tokenStorage.saveToken(token);

        return newToken;
    }

    @Override
    public boolean exists(TokenDTO token) {
        token = Objects.requireNonNull(token);

        return tokenStorage.exists(modelMapper.map(token, Token.class));
    }

    @Override
    public void delete(TokenDTO token) {
        token = Objects.requireNonNull(token);

        tokenStorage.deleteToken(modelMapper.map(token, Token.class));
    }

    @Override
    public List<TokenDTO> getAllTokens() {
        return tokenStorage.getAllTokens().stream()
                .map(t -> modelMapper.map(t, TokenDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TokenDTO> getTokenByTokenString(String tokenString) {
        tokenString = Objects.requireNonNull(tokenString);

        Optional<Token> tokenEntity = tokenStorage.getTokenByTokenString(tokenString);
        if (tokenEntity.isPresent()) {
            return Optional.of(modelMapper.map(tokenEntity.get(), TokenDTO.class));
        } else {
            return Optional.empty();
        }
    }

    private LocalDateTime getCurrentExpireDate() {
        return LocalDateTime.now().with(temporal -> temporal.plus(EXPIRE_MINUTES, MINUTES));
    }

}
