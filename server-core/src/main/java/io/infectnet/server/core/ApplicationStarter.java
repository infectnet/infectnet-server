package io.infectnet.server.core;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.options;
import static spark.Spark.webSocket;

import io.infectnet.server.common.configuration.Configuration;
import io.infectnet.server.common.configuration.ConfigurationCreationException;
import io.infectnet.server.common.configuration.ConfigurationHolder;
import io.infectnet.server.common.configuration.PropertiesConfiguration;
import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.exception.ExceptionMapperController;
import io.infectnet.server.controller.websocket.WebSocketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import spark.Spark;

class ApplicationStarter {
  private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

  private static final String configurationPath = "configuration.properties";

  private final Set<RestController> restControllers;

  private final ExceptionMapperController exceptionMapperController;

  @Inject
  ApplicationStarter(Set<RestController> restControllers,
                     ExceptionMapperController exceptionMapperController) {
    this.restControllers = restControllers;

    this.exceptionMapperController = exceptionMapperController;
  }

  void start() {
    Optional<Configuration> configuration = ensureConfiguration();

    if (!configuration.isPresent()) {
      Spark.stop();

      return;
    }

    ConfigurationHolder.INSTANCE.setActiveConfiguration(configuration.get());

    // Must be defined before regular HTTP routes!
    webSocket("/ws", WebSocketController.class);

    enableCORS("*", "*", "*");

    restControllers.forEach(RestController::configure);

    exceptionMapperController.configure();

    // Note that this WILL NOT be called if an exception occurs
    // and the ExceptionMapperController handles it
    after((request, response) -> response.type("application/json"));

    logger.info("Controllers configured!");
  }

  private Optional<Configuration> ensureConfiguration() {
    Optional<Configuration> configOptional = loadFileConfiguration();

    if (configOptional.isPresent()) {
      return configOptional;
    }

    return loadDefaultConfiguration();
  }

  private Optional<Configuration> loadFileConfiguration() {
    try {
      Configuration configuration = PropertiesConfiguration.fromFile(configurationPath);

      return Optional.of(configuration);
    } catch (ConfigurationCreationException e) {
      logger.warn("{}", e.toString());

      return Optional.empty();
    }
  }

  private Optional<Configuration> loadDefaultConfiguration() {
    try {
      InputStream stream = this.getClass().getClassLoader().getResourceAsStream(configurationPath);

      Configuration configuration = PropertiesConfiguration.fromStream(stream);

      return Optional.of(configuration);
    } catch (ConfigurationCreationException e) {
      logger.warn("{}", e.toString());

      return Optional.empty();
    }
  }

  // Enables CORS on requests. This method is an initialization method and should be called once.
  private void enableCORS(final String origin, final String methods, final String headers) {

    options("/*", (request, response) -> {

      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    before((request, response) -> {
      response.header("Access-Control-Allow-Origin", origin);
      response.header("Access-Control-Request-Method", methods);
      response.header("Access-Control-Allow-Headers", headers);
    });
  }
}
