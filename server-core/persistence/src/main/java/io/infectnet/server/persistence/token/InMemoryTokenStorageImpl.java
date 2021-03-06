package io.infectnet.server.persistence.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * {@link TokenStorage} in-memory implementation.
 */
public class InMemoryTokenStorageImpl implements TokenStorage {


  private List<Token> tokens;

  public InMemoryTokenStorageImpl() {
    this.tokens = new ArrayList<>();
  }

  @Override
  public Optional<Token> getTokenByTokenString(String token) {
    return tokens.stream()
        .filter(t -> t.getToken().equals(token))
        .findFirst();
  }

  @Override
  public List<Token> getAllTokens() {
    return Collections.unmodifiableList(tokens);
  }

  @Override
  public boolean exists(Token token) {
    return tokens.stream()
        .filter(t -> t.getToken().equals(token.getToken()))
        .count() != 0;
  }

  @Override
  public void saveToken(Token token) {
    if (!exists(token)) {
      tokens.add(token);
    } else {
      throw new IllegalArgumentException("The token string is not unique!");
    }
  }

  @Override
  public void deleteToken(Token token) {
    tokens.remove(token);
  }
}
