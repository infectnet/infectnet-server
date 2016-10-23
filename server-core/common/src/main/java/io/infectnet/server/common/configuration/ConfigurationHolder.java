package io.infectnet.server.common.configuration;

/**
 * Singleton class that can be used to retrieve the currently active {@link Configuration}.
 */
public enum ConfigurationHolder {
  INSTANCE;

  private Configuration activeConfiguration;

  /**
   * Sets the active configuration to the given configuration if it's not already set.
   * @param configuration the new configuration to be used
   */
  public void setActiveConfiguration(Configuration configuration) {
    // Cannot be changed once set
    if (configuration != null) {
      this.activeConfiguration = configuration;
    }
  }

  /**
   * Gets the currently active configuration.
   * @return the active configuration
   */
  public Configuration getActiveConfiguration() {
    return activeConfiguration;
  }
}
