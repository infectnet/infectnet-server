package io.infectnet.server.service.admin

import io.infectnet.server.common.configuration.Configuration
import io.infectnet.server.common.configuration.ConfigurationHolder
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import spock.lang.Specification

import java.time.Instant

class JwtAuthenticationServiceImplTest extends Specification {
  def final JWT_SECRET_KEY = "admin_jwt_secret";

  def final JWT_USERNAME_FIELD = "username";

  def final JWT_ISSUER = "infectnet";

  def final USERNAME_KEY = "admin_username";

  def final PASSWORD_KEY = "admin_password";

  def final REAL_USERNAME = "admin";
  def final REAL_PASSWORD = "password";
  def final JWT_SECRET = "secret";

  def jwtAuthenticationService;

  def setup() {
    jwtAuthenticationService = new JwtAuthenticationServiceImpl();

    Configuration c = Mock(Configuration);

    c.get(USERNAME_KEY) >> "admin";
    c.get(PASSWORD_KEY) >> "password";
    c.get(JWT_SECRET_KEY) >> "secret";

    ConfigurationHolder.INSTANCE.activeConfiguration = c;
  }

  def "login returns an empty Optional if the username does not match"() {
    given: "a wrong username and the right password pair"
      def username = "user";
      def password = REAL_PASSWORD;

    when: "the wrong credentials get passed to login"
      def result = jwtAuthenticationService.login(username, password);

    then: "an empty Optional is returned"
      result == Optional.empty();
  }

  def "login returns an empty Optional if the password does not match"() {
    given: "the right username and a wrong password pair"
      def username = REAL_USERNAME;
      def password = "pw";

    when: "the wrong credentials get passed to login"
      def result = jwtAuthenticationService.login(username, password);

    then: "an empty Optional is returned"
      result == Optional.empty();
  }

  def "login returns an Optional with a valid token if the credentials match"() {
    when: "login gets called with the right credentials"
      def result = jwtAuthenticationService.login(REAL_USERNAME, REAL_PASSWORD);

    then: "the returned Optional contains a valid token"
      result.isPresent();

      Jwts.parser()
          .requireIssuer(JWT_ISSUER)
          .require(JWT_USERNAME_FIELD, REAL_USERNAME)
          .setSigningKey(JWT_SECRET.getBytes())
          .parseClaimsJws(result.get());
  }

  def "isAuthenticated throws NullPointerException if the token is null"() {
    when: "isAuthenticated gets called with null"
      jwtAuthenticationService.isAuthenticated(null);

    then: "NullPointerException is thrown"
      thrown(NullPointerException.class);
  }

  def "isAuthenticated returns false if the token's issuer does not match"() {
    given: "claims with fake issuer and an invalid token"
      Claims claims = Jwts.claims().setIssuer("fake");

      claims.put(JWT_USERNAME_FIELD, REAL_USERNAME);

      def token = Jwts.builder()
          .signWith(SignatureAlgorithm.HS256, JWT_SECRET.bytes)
          .setClaims(claims)
          .setExpiration(Date.from(Instant.now().plusSeconds(60)))
          .compact();

    expect: "isAuthenticated returns false"
      !jwtAuthenticationService.isAuthenticated(token);
  }

  def "isAuthenticated returns false if the token's username does not match"() {
    given: "claims with fake username and an invalid token"
      Claims claims = Jwts.claims().setIssuer(JWT_ISSUER);

      claims.put(JWT_USERNAME_FIELD, "fake");

      def token = Jwts.builder()
          .signWith(SignatureAlgorithm.HS256, JWT_SECRET.bytes)
          .setClaims(claims)
          .setExpiration(Date.from(Instant.now().plusSeconds(60)))
          .compact();

    expect: "isAuthenticated returns false"
      !jwtAuthenticationService.isAuthenticated(token);
  }

  def "isAuthenticated returns false if the token is expired"() {
    given: "an expired token with correct claims"
      Claims claims = Jwts.claims().setIssuer(JWT_ISSUER);

      claims.put(JWT_USERNAME_FIELD, REAL_USERNAME);

      def token = Jwts.builder()
          .signWith(SignatureAlgorithm.HS256, JWT_SECRET.bytes)
          .setClaims(claims)
          .setExpiration(Date.from(Instant.now().minusSeconds(60)))
          .compact();

    expect: "isAuthenticated returns false"
      !jwtAuthenticationService.isAuthenticated(token);
  }

  def "isAuthenticated returns true if the token is not expired and contains the right claims"() {
    given: "a completely valid token"
      Claims claims = Jwts.claims().setIssuer(JWT_ISSUER);

      claims.put(JWT_USERNAME_FIELD, REAL_USERNAME);

      def token = Jwts.builder()
          .signWith(SignatureAlgorithm.HS256, JWT_SECRET.bytes)
          .setClaims(claims)
          .setExpiration(Date.from(Instant.now().plusSeconds(60)))
          .compact();

    expect: "isAuthenticated returns true"
      jwtAuthenticationService.isAuthenticated(token);
  }

  def "renewToken throws NullPointerException if the token is null"() {
    when: "renewToken gets called with null"
      jwtAuthenticationService.renewToken(null);

    then: "NullPointerException is thrown"
      thrown(NullPointerException.class);
  }

  def "renewToken returns an empty Optional if called with an expired token"() {
    given: "an expired token with correct claims"
      Claims claims = Jwts.claims().setIssuer(JWT_ISSUER);

      claims.put(JWT_USERNAME_FIELD, REAL_USERNAME);

      def token = Jwts.builder()
          .signWith(SignatureAlgorithm.HS256, JWT_SECRET.bytes)
          .setClaims(claims)
          .setExpiration(Date.from(Instant.now().minusSeconds(60)))
          .compact();

    when: "renewToken is called with the token"
      def result = jwtAuthenticationService.renewToken(token);

    then: "an empty Optional is returned"
      !result.isPresent();
  }

  def "renewToken returns a valid token if called with a valid token"() {
    given: "a completely valid token"
      Claims claims = Jwts.claims().setIssuer(JWT_ISSUER);

      claims.put(JWT_USERNAME_FIELD, REAL_USERNAME);

      def token = Jwts.builder()
          .signWith(SignatureAlgorithm.HS256, JWT_SECRET.bytes)
          .setClaims(claims)
          .setExpiration(Date.from(Instant.now().plusSeconds(60)))
          .compact();

    when: "renewToken is called with the token"
      def result = jwtAuthenticationService.renewToken(token);

    then: "a valid token is returned"
      result.isPresent();

      Jwts.parser()
          .requireIssuer(JWT_ISSUER)
          .require(JWT_USERNAME_FIELD, REAL_USERNAME)
          .setSigningKey(JWT_SECRET.getBytes())
          .parseClaimsJws(result.get());
  }
}