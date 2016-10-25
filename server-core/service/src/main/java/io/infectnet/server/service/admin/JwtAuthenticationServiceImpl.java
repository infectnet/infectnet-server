package io.infectnet.server.service.admin;

import io.infectnet.server.common.configuration.Configuration;
import io.infectnet.server.common.configuration.ConfigurationHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * JWT implementation of the {@code AuthenticationService}.
 */
public class JwtAuthenticationServiceImpl implements AuthenticationService {
  private static final String JWT_SECRET_KEY = "admin_jwt_secret";

  private static final String JWT_USERNAME_FIELD = "username";

  private static final String JWT_ISSUER = "infectnet";

  @Override
  public Optional<String> login(String username, String password) {
    if (credentialsMatch(username, password)) {
      AdminCredentials credentials = AdminCredentials.fetchFromConfiguration();

      String token = produceToken(credentials);

      return Optional.of(token);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public boolean isAuthenticated(String token) {
    final String nonNullToken = Objects.requireNonNull(token);

    AdminCredentials credentials = AdminCredentials.fetchFromConfiguration();

    try {
      Jwts.parser()
          .requireIssuer(JWT_ISSUER)
          .require(JWT_USERNAME_FIELD, credentials.getUsername())
          .setSigningKey(fetchJwtSecret().getBytes())
          .parseClaimsJws(nonNullToken);

      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public Optional<String> renewToken(String token) {
    if (!isAuthenticated(Objects.requireNonNull(token))) {
      return Optional.empty();
    } else {
      String newToken = produceToken(AdminCredentials.fetchFromConfiguration());

      return Optional.of(newToken);
    }
  }

  private String produceToken(AdminCredentials credentials) {
    Claims claims = Jwts.claims().setIssuer(JWT_ISSUER);

    claims.put(JWT_USERNAME_FIELD, credentials.getUsername());

    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, fetchJwtSecret().getBytes())
        .setClaims(claims)
        .setExpiration(calculateExpirationDate())
        .compact();
  }

  private Date calculateExpirationDate() {
    Instant expirationInstant = Instant.now().plus(1, ChronoUnit.HOURS);

    return Date.from(expirationInstant);
  }

  private boolean credentialsMatch(String username, String password) {
    AdminCredentials credentials = AdminCredentials.fetchFromConfiguration();

    return username.equals(credentials.getUsername())
        && password.equals(credentials.getPassword());
  }

  private String fetchJwtSecret() {
    Configuration config = ConfigurationHolder.INSTANCE.getActiveConfiguration();

    return config.get(JWT_SECRET_KEY);
  }

  private static class AdminCredentials {
    private static final String USERNAME_KEY = "admin_username";

    private static final String PASSWORD_KEY = "admin_password";

    private final String username;

    private final String password;

    static AdminCredentials fetchFromConfiguration() {
      Configuration config = ConfigurationHolder.INSTANCE.getActiveConfiguration();

      return new AdminCredentials(config.get(USERNAME_KEY), config.get(PASSWORD_KEY));
    }

    private AdminCredentials(String username, String password) {
      this.username = username;

      this.password = password;
    }

    String getUsername() {
      return username;
    }

    String getPassword() {
      return password;
    }
  }
}
