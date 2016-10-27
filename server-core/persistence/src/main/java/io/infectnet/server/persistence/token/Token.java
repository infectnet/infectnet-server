package io.infectnet.server.persistence.token;

import java.time.LocalDateTime;

public class Token {

  private String token;

  private LocalDateTime expirationDateTime;

  /**
   * Constructs a new {@link Token} object.
   */
  public Token() {

  }

  /**
   * Constructs a new {@link Token} object.
   * @param token the actual token
   * @param expirationDateTime the expiration date
   */
  public Token(String token, LocalDateTime expirationDateTime) {
    this.token = token;
    this.expirationDateTime = expirationDateTime;
  }

  /**
   * Returns the actual token as a string.
   * @return the actual token
   */
  public String getToken() {
    return token;
  }

  /**
   * Returns the expiration date for the token.
   * @return the expiration date
   */
  public LocalDateTime getExpirationDateTime() {
    return expirationDateTime;
  }

  /**
   * Sets the actual token.
   * @param token the actual token
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Sets the expiration date to the token.
   * @param expirationDateTime the expiration date
   */
  public void setExpirationDateTime(LocalDateTime expirationDateTime) {
    this.expirationDateTime = expirationDateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Token token1 = (Token) o;

    if (!token.equals(token1.token)) {
      return false;
    }
    return expirationDateTime.equals(token1.expirationDateTime);

  }

  @Override
  public int hashCode() {
    int result = token.hashCode();
    result = 31 * result + expirationDateTime.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Token{" +
        "token='" + token + '\'' +
        ", expirationDateTime=" + expirationDateTime +
        '}';
  }
}
