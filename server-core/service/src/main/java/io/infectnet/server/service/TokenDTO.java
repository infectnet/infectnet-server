package io.infectnet.server.service;

import java.time.LocalDateTime;

public class TokenDTO {

    private final String token;

    private final LocalDateTime expirationDateTime;

    /**
     * Constructs a new {@link TokenDTO} object.
     *
     * @param token              the actual token
     * @param expirationDateTime the expiration date
     */
    public TokenDTO(String token, LocalDateTime expirationDateTime) {
        this.token = token;
        this.expirationDateTime = expirationDateTime;

    }

    /**
     * Returns the actual token as a string.
     *
     * @return the actual token
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the expiration date for the token.
     *
     * @return the expiration date
     */
    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenDTO tokenDTO = (TokenDTO) o;

        if (!token.equals(tokenDTO.token)) return false;
        return expirationDateTime.equals(tokenDTO.expirationDateTime);

    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + expirationDateTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TokenDTO{" +
                "token='" + token + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                '}';
    }
}
