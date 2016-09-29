package io.infectnet.server.controller.impl;

import io.infectnet.server.controller.RestController;

import static spark.Spark.get;

/**
 * REST endpoint responsible for token publishing.
 */
public class TokenController implements RestController {

    @Override
    public void configure() {
        get("/token", (req, resp) -> "Token!");
    }
}
