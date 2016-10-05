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
        TokenDTO newToken = new TokenDTO(RandomStringUtils.random(TOKEN_LENGTH), getCurrentExpireDate());

        Token token = modelMapper.map(newToken, Token.class);

        tokenStorage.saveToken(token);

        return newToken;
    }

    @Override
    public boolean exists(TokenDTO token) {
        return tokenStorage.exists(modelMapper.map(token, Token.class));
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

    @Override
    public Optional<TokenDTO> getTokenByTokenString(String token) {
        Optional<Token> tokenEntity = tokenStorage.getTokenByTokenString(token);
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
