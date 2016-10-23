package io.infectnet.server.common.configuration;

/**
 * Common interface for classes that can provide a key based retrieval of various configuration
 * values.
 */
public interface Configuration {
  /**
   * Gets the value associated with the specified key.
   * @param key the key we're searching for
   * @return the value associated with the key
   */
  String get(String key);
}
