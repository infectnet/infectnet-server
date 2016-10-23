package io.infectnet.server.controller.user;

import static spark.Spark.post;

import com.google.gson.Gson;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.service.token.TokenService;
import io.infectnet.server.service.user.UserService;

import spark.Request;
import spark.Response;

/**
 * REST controller responsible for registration.
 */
public class RegistrationController implements RestController {

  private static final String URL_PATH = "/register";

  private final TokenService tokenService;

  private final UserService userService;

  public RegistrationController(TokenService tokenService,
                                UserService userService) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @Override
  public void configure() {
    post(URL_PATH, this::registrationEndpoint);
  }

  private Object registrationEndpoint(Request req, Response res) {
    return null;
  }
}
