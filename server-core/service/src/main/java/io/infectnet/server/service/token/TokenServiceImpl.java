package io.infectnet.server.service.token;

import static java.time.temporal.ChronoUnit.MINUTES;

import io.infectnet.server.persistence.token.Token;
import io.infectnet.server.persistence.token.TokenStorage;
import io.infectnet.server.service.converter.ConverterService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Default {@link TokenService} implementation.
 */
public class TokenServiceImpl implements TokenService {

  private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

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
      tokenString = RandomStringUtils.randomAlphabetic(TOKEN_LENGTH);
    }
    while (tokenStorage.getTokenByTokenString(tokenString).isPresent());

    TokenDTO newToken = new TokenDTO(tokenString, getCurrentExpireDate());

    Token token = converterService.map(newToken, Token.class);

    tokenStorage.saveToken(token);

    logger.info("New token created: {}", token);

    return newToken;
  }

  @Override
  public boolean exists(TokenDTO token) {
    Token tokenEntity = converterService.map(Objects.requireNonNull(token), Token.class);
    return isValidToken(tokenEntity) && tokenStorage.exists(tokenEntity);
  }

  /**
   * {@inheritDoc}
   * <p>
   * This implementation also deletes all expired tokens
   * from the storage beside the specified token.
   * @param token the token to delete
   */
  @Override
  public void delete(TokenDTO token) {
    tokenStorage.deleteToken(converterService.map(Objects.requireNonNull(token), Token.class));

    tokenStorage.getAllTokens().stream()
        .filter(t -> !isValidToken(t))
        .forEach(tokenStorage::deleteToken);
  }

  @Override
  public List<TokenDTO> getAllTokens() {
    return tokenStorage.getAllTokens().stream()
        .filter(this::isValidToken)
        .map(t -> converterService.map(t, TokenDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<TokenDTO> getTokenByTokenString(String tokenString) {
    Optional<Token>
        tokenEntity =
        tokenStorage.getTokenByTokenString(Objects.requireNonNull(tokenString));
    if (tokenEntity.isPresent() && isValidToken(tokenEntity.get())) {
      return Optional.of(converterService.map(tokenEntity.get(), TokenDTO.class));
    } else {
      return Optional.empty();
    }
  }

  private LocalDateTime getCurrentExpireDate() {
    return LocalDateTime.now().with(temporal -> temporal.plus(EXPIRE_MINUTES, MINUTES));
  }

  private boolean isValidToken(Token token) {
    return token.getExpirationDateTime().isAfter(LocalDateTime.now());
  }

}
