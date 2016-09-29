package io.infectnet.server.controller.impl;

import io.infectnet.server.controller.RestController;

import static spark.Spark.post;

/**
 * REST controller responsible for registration.
 */
public class RegistrationController implements RestController {

    @Override
    public void configure() {
        post("/register", (req, resp) -> "Registration!");
    }
}
