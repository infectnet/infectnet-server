package io.infectnet.server.core;

import static spark.Spark.after;

import io.infectnet.server.controller.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import javax.inject.Inject;

class ApplicationStarter {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

  private final Set<RestController> restControllers;

  @Inject
  ApplicationStarter(Set<RestController> restControllers) {
    this.restControllers = restControllers;
  }

  void start() {
    restControllers.forEach(RestController::configure);

    after((request, response) -> {
      response.type("application/json");
    });

    logger.info("Controllers configured!");

  }

}
