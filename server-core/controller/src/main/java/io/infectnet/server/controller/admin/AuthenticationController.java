package io.infectnet.server.controller.admin;

import static spark.Spark.before;
import static spark.Spark.post;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.service.admin.AuthenticationService;

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

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  public void configure() {
    before(ROUTE_PREFIX + "/*", this::filterRequests);
  }

  private void filterRequests(Request req, Response resp)
      throws MissingTokenException, UnauthorizedTokenException {
    // requests to the login URL does not need to be authenticated
    if (req.pathInfo().equals(LOGIN_URL)) {
      return;
    }

    Optional<String> tokenOptional = extractToken(req);

    if (!tokenOptional.isPresent()) {
      throw new MissingTokenException(req.pathInfo());
    }

    if (!authenticationService.isAuthenticated(tokenOptional.get())) {
      throw new UnauthorizedTokenException(req.pathInfo());
    }
  }

  private Optional<String> extractToken(Request req) {
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
}
