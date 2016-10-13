package io.infectnet.server.controller;

/**
 * Interface used for creating REST endpoints.
 * All controllers using REST should implement this interface.
 */
public interface RestController {

  /**
   * Configures and starts the REST endpoint.
   */
  void configure();

}
