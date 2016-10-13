package io.infectnet.server.controller.impl;

import static spark.Spark.get;

import io.infectnet.server.controller.RestController;

/**
 * REST endpoint responsible for token publishing.
 */
public class TokenController implements RestController {

  @Override
  public void configure() {
    get("/token", (req, resp) -> "Token!");
  }
}
