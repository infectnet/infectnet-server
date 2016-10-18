package io.infectnet.server.controller.user;

import static spark.Spark.post;

import io.infectnet.server.controller.RestController;

/**
 * REST controller responsible for registration.
 */
public class RegistrationController implements RestController {

  @Override
  public void configure() {
    post("/register", (req, resp) -> "Registration!");
  }
}
