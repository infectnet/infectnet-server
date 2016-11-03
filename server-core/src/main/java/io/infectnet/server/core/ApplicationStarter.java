package io.infectnet.server.core;

import io.infectnet.server.common.configuration.Configuration;
import io.infectnet.server.common.configuration.ConfigurationHolder;
import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.exception.ExceptionMapperController;
import io.infectnet.server.controller.websocket.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

import static spark.Spark.after;
import static spark.Spark.webSocket;

class ApplicationStarter {
  private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

  private final Set<RestController> restControllers;

  private final ExceptionMapperController exceptionMapperController;

  private final Dispatcher dispatcher;

  @Inject
  ApplicationStarter(Set<RestController> restControllers,
                     ExceptionMapperController exceptionMapperController,
                     Dispatcher dispatcher) {
    this.restControllers = restControllers;

    this.exceptionMapperController = exceptionMapperController;

    this.dispatcher = dispatcher;
  }

  void start() {
    Optional<Configuration> configuration = new ConfigurationLoader().loadConfiguration();

    if (!configuration.isPresent()) {
      Spark.stop();

      return;
    }

    ConfigurationHolder.INSTANCE.setActiveConfiguration(configuration.get());

    // Must be defined before regular HTTP routes!
    webSocket("/ws", dispatcher);

    // CORS only should be enabled after WebSocket initialization
    CorsSupporter.enableCORS();

    restControllers.forEach(RestController::configure);

    exceptionMapperController.configure();

    // Note that this WILL NOT be called if an exception occurs
    // and the ExceptionMapperController handles it
    after((request, response) -> response.type("application/json"));

    logger.info("Controllers configured!");
  }


}
