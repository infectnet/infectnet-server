package io.infectnet.server.controller.websocket.code;

import io.infectnet.server.engine.core.script.generation.CompilationError;

import java.util.List;

/**
 * Represents the server's response when the client uploaded new source code.
 */
public class CompilationResults {

  private final List<CompilationError> errors;

  public CompilationResults(
      List<CompilationError> errors) {
    this.errors = errors;
  }
}
