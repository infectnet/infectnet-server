package io.infectnet.server.controller.admin;

import static spark.Spark.before;
import static spark.Spark.post;

import com.google.gson.Gson;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.service.admin.AuthenticationService;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import spark.Request;
import spark.Response;

public class AuthenticationController implements RestController {
  private static final String ROUTE_PREFIX = "/admin";

  private static final String LOGIN_URL = ROUTE_PREFIX + "/login";

  private static final String RENEW_URL = ROUTE_PREFIX + "/renew";

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final Pattern AUTH_PATTERN = Pattern.compile("Bearer (.+)");

  private final AuthenticationService authenticationService;

  private final Gson gson;

  public AuthenticationController(AuthenticationService authenticationService, Gson gson) {
    this.authenticationService = authenticationService;

    this.gson = gson;
  }

  @Override
  public void configure() {
    before(ROUTE_PREFIX + "/*", this::filterRequests);

    post(LOGIN_URL, this::login, gson::toJson);

    post(RENEW_URL, this::renew, gson::toJson);
  }

  private void filterRequests(Request req, Response resp)
      throws MissingTokenException, UnauthorizedTokenException {
    // requests to the login URL does not need to be authenticated
    if (req.pathInfo().equals(LOGIN_URL)) {
      return;
    }

    Optional<String> tokenOptional = extractTokenFromHeader(req);

    if (!tokenOptional.isPresent()) {
      throw new MissingTokenException(req.pathInfo());
    }

    if (!authenticationService.isAuthenticated(tokenOptional.get())) {
      throw new UnauthorizedTokenException(req.pathInfo());
    }
  }

  private Object login(Request req, Response resp) throws LoginFailedException {
    LoginCredentials credentials = gson.fromJson(req.body(), LoginCredentials.class);

    Optional<String> tokenOptional =
        authenticationService.login(credentials.getUsername(), credentials.getPassword());

    if (!tokenOptional.isPresent()) {
      throw new LoginFailedException();
    }

    return Collections.singletonMap("token", tokenOptional.get());
  }

  private Object renew(Request req, Response resp) throws UnauthorizedTokenException {
    Optional<String> oldTokenOptional = extractTokenFromHeader(req);

    // At this point we can be sure, that the header contains a token
    // but better be safe than sorry.
    if (!oldTokenOptional.isPresent()) {
      throw new UnauthorizedTokenException(RENEW_URL);
    }

    Optional<String> newTokenOptional =
        authenticationService.renewToken(oldTokenOptional.get());

    if (!newTokenOptional.isPresent()) {
      throw new UnauthorizedTokenException(RENEW_URL);
    }

    return Collections.singletonMap("token", newTokenOptional.get());
  }

  private Optional<String> extractTokenFromHeader(Request req) {
    String authHeader = req.headers(AUTHORIZATION_HEADER);

    if (authHeader == null) {
      return Optional.empty();
    }

    Matcher headerMatcher = AUTH_PATTERN.matcher(authHeader);

    if (!headerMatcher.matches()) {
      return Optional.empty();
    }

    return Optional.ofNullable(headerMatcher.group(1));
  }

  private static class LoginCredentials {
    private String username;

    private String password;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}
