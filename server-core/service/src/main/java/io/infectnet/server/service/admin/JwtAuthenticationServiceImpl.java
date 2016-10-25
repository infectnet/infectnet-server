package io.infectnet.server.service.admin;

import io.infectnet.server.common.configuration.Configuration;
import io.infectnet.server.common.configuration.ConfigurationHolder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

public class JwtAuthenticationServiceImpl implements AuthenticationService {
  private static final String JWT_SECRET_KEY = "admin_jwt_secret";

  private final Base64.Encoder base64Encoder;

  public JwtAuthenticationServiceImpl() {
    this.base64Encoder = Base64.getEncoder();
  }

  @Override
  public Optional<String> login(String username, String password) {


    return null;
  }

  @Override
  public boolean isAuthenticated(String token) {
    final String nonNullToken = Objects.requireNonNull(token);

    try {
      Jwts.parser()
          .setSigningKey(base64Encoder.encode(fetchJwtSecret().getBytes()))
          .parse(nonNullToken);

      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public Optional<String> renewToken(String token) {
    return null;
  }

  private String fetchJwtSecret() {
    Configuration config = ConfigurationHolder.INSTANCE.getActiveConfiguration();

    return config.get(JWT_SECRET_KEY);
  }
}
