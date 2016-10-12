package io.infectnet.server.service.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.service.ConverterService;
import io.infectnet.server.service.TokenDTO;
import io.infectnet.server.service.TokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

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

    private final ConverterService converterService;

    private final TokenStorage tokenStorage;

    @Inject
    public TokenServiceImpl(TokenStorage tokenStorage, ConverterService converterService) {
        this.tokenStorage = tokenStorage;
        this.converterService = converterService;
    }

    @Override
    public TokenDTO createNewToken() {
        String tokenString = StringUtils.EMPTY;

        do {
            tokenString = RandomStringUtils.random(TOKEN_LENGTH);
        }
        while (tokenStorage.getTokenByTokenString(tokenString).isPresent());

        TokenDTO newToken = new TokenDTO(tokenString, getCurrentExpireDate());

        Token token = converterService.map(newToken, Token.class);

        tokenStorage.saveToken(token);

        return newToken;
    }

    @Override
    public boolean exists(TokenDTO token) {
        return tokenStorage.exists(converterService.map(Objects.requireNonNull(token), Token.class));
    }

    @Override
    public void delete(TokenDTO token) {
        tokenStorage.deleteToken(converterService.map(Objects.requireNonNull(token), Token.class));
    }

    @Override
    public List<TokenDTO> getAllTokens() {
        return tokenStorage.getAllTokens().stream()
                .map(t -> converterService.map(t, TokenDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TokenDTO> getTokenByTokenString(String tokenString) {
        Optional<Token> tokenEntity = tokenStorage.getTokenByTokenString(Objects.requireNonNull(tokenString));
        if (tokenEntity.isPresent()) {
            return Optional.of(converterService.map(tokenEntity.get(), TokenDTO.class));
        } else {
            return Optional.empty();
        }
    }

    private LocalDateTime getCurrentExpireDate() {
        return LocalDateTime.now().with(temporal -> temporal.plus(EXPIRE_MINUTES, MINUTES));
    }

}
