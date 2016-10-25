package io.infectnet.server.common.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Configuration class that uses a backing {@link java.util.Properties} object to serve requests.
 */
public class PropertiesConfiguration implements Configuration {
  private final Properties properties;

  /**
   * Creates a new instance by loading the properties file on the given path.
   * @param path the path that points to a properties file
   * @return a new configuration that contains keys from the loaded file
   * @throws ConfigurationCreationException if the file is not found or there's an IO when loading
   * its contents
   * @throws IllegalArgumentException if the path points to a directory
   * @throws NullPointerException is the path is null
   */
  public static PropertiesConfiguration fromFile(String path)
      throws ConfigurationCreationException {
    File file = new File(Objects.requireNonNull(path));

    if (file.isDirectory()) {
      throw new IllegalArgumentException("The path must not point to a directory!");
    }

    FileInputStream stream;

    try {
      stream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new ConfigurationCreationException(e);
    }

    return fromStream(stream);
  }

  /**
   * Creates a new instance from the contents of the specified stream.
   * @param inputStream the stream that acts as a source
   * @return a new configuration that contains the keys loaded from the stream
   * @throws ConfigurationCreationException if an I/O error occurs when processing the stream
   */
  public static PropertiesConfiguration fromStream(InputStream inputStream)
      throws ConfigurationCreationException {
    Properties props = new Properties();

    try {
      props.load(inputStream);
    } catch (IOException e) {
      throw new ConfigurationCreationException(e);
    }

    return new PropertiesConfiguration(props);
  }

  private PropertiesConfiguration(Properties properties) {
    this.properties = properties;
  }

  @Override
  public String get(String key) {
    return properties.getProperty(Objects.requireNonNull(key));
  }
}
