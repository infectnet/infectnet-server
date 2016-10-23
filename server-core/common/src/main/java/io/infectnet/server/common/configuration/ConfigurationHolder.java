package io.infectnet.server.common.configuration;

public enum ConfigurationHolder {
  INSTANCE;

  Configuration activeConfiguration;

  public void setActiveConfiguration(Configuration configuration) {
    if (configuration != null) {
      this.activeConfiguration = configuration;
    }
  }

  public Configuration getActiveConfiguration() {
    return activeConfiguration;
  }
}
