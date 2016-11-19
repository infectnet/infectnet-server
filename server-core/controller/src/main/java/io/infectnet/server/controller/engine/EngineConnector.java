package io.infectnet.server.controller.engine;

import io.infectnet.server.engine.Engine;
import io.infectnet.server.service.user.UserDTO;

import java.util.concurrent.CompletableFuture;

/**
 * Interface to describe a mediator object between the {@link Engine} and controller layer.
 */
public interface EngineConnector {

  /**
   * Starts the engine.
   */
  void start();

  /**
   * Stops the engine asynchronously or blocking while it is not stopped.
   * Will always return true if stop is asynchronous.
   * @param stopType describes how to stop: blocking or async
   * @return true if the engine is stopped, false otherwise
   */
  boolean stop(StopType stopType);

  /**
   * Compiles source code and saves it for the given user.
   * @param user the user who uploaded the source code
   * @param source the source code
   */
  CompletableFuture<Void> compileAndUploadForUser(UserDTO user, String source);

  /**
   * Stop type for choosing the method for stopping the engine.
   */
  enum StopType {
    BLOCKING,
    ASYNC
  }

}
