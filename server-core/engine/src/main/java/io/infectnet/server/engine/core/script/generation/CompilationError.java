package io.infectnet.server.engine.core.script.generation;

/**
 * Represents a syntax error occured during player code compilation.
 */
public class CompilationError {

  private final int lineNumber;

  private final int columnNumber;

  private final String message;

  public CompilationError(int lineNumber, int columnNumber, String message) {
    this.lineNumber = lineNumber;
    this.columnNumber = columnNumber;
    this.message = message;
  }
}
