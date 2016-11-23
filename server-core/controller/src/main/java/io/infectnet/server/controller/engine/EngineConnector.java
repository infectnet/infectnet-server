package io.infectnet.server.controller.engine;

import io.infectnet.server.engine.Engine;
import io.infectnet.server.engine.core.script.generation.CompilationError;
import io.infectnet.server.service.user.UserDTO;

import java.util.List;
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
   * Stop type for choosing the method for stopping the engine.
   */
  enum StopType {
    BLOCKING,
    ASYNC
  }

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
  CompletableFuture<List<CompilationError>> compileAndUploadForUser(UserDTO user, String source);

  /**
   * Provides the currently uploaded source code of a user.
   * @param user the user
   * @return the source code
   */
  String getSourceCodeForUser(UserDTO user);


  /**
   * Sets the user as observed.
   * @param user the {@code UserDTO} to be set as observed
   */
  void setUserAsObserved(UserDTO user);

  /**
   * Removes the user from the list of observed {@code UserDTO}s.
   * @param user the {@code UserDTO} to be removed
   */
  void removeUserFromObserved(UserDTO user);
}
