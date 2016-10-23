package io.infectnet.server.controller.user;

import static io.infectnet.server.controller.utils.ResponseUtils.EMPTY_OK;
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

  private final Gson gson;

  public RegistrationController(TokenService tokenService,
                                UserService userService, Gson gson) {
    this.tokenService = tokenService;
    this.userService = userService;

    this.gson = gson;
  }

  @Override
  public void configure() {
    post(URL_PATH, this::registrationEndpoint);
  }

  private Object registrationEndpoint(Request req, Response res) {
    RegistrationDetails details = gson.fromJson(req.body(), RegistrationDetails.class);

    try {
      userService.register(details.getToken(), details.getEmail(), details.getUsername(),
        details.getPassword());

      res.status(200);

      return EMPTY_OK;
    } catch (Exception e) {
      res.status(400);

      return EMPTY_OK;
    }
  }
}
