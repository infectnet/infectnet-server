package io.infectnet.server.core;

import io.infectnet.server.common.configuration.Configuration;
import io.infectnet.server.common.configuration.ConfigurationCreationException;
import io.infectnet.server.common.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Optional;

/**
 * Responsible for loading configuration file or default configuration.
 */
public class ConfigurationLoader {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

  private static final String CONFIGURATION_PATH = "configuration.properties";

  /**
   * Returns the server settings loaded from {@code configuration.properties} if available.
   * Otherwise a default configuration is loaded.
   * @return the available server configuration
   */
  public Optional<Configuration> loadConfiguration() {
    Optional<Configuration> configOptional = loadFileConfiguration();

    if (configOptional.isPresent()) {
      return configOptional;
    }

    return loadDefaultConfiguration();
  }

  private Optional<Configuration> loadFileConfiguration() {
    try {
      Configuration configuration = PropertiesConfiguration.fromFile(CONFIGURATION_PATH);

      return Optional.of(configuration);
    } catch (ConfigurationCreationException e) {
      logger.warn("{}", e.toString());

      return Optional.empty();
    }
  }

  private Optional<Configuration> loadDefaultConfiguration() {
    try {
      InputStream stream = this.getClass().getClassLoader().getResourceAsStream(CONFIGURATION_PATH);

      Configuration configuration = PropertiesConfiguration.fromStream(stream);

      return Optional.of(configuration);
    } catch (ConfigurationCreationException e) {
      logger.warn("{}", e.toString());

      return Optional.empty();
    }
  }

}
