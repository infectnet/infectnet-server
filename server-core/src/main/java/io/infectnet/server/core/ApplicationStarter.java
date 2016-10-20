package io.infectnet.server.core;

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

    logger.info("Controllers configured!");

  }

}
