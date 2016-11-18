package io.infectnet.server.controller.websocket.game;

import io.infectnet.server.controller.utils.error.Error;
import io.infectnet.server.controller.utils.error.ErrorConvertibleException;

/**
 * Exception thrown when code uploaded by the user has syntax errors and we notify the user.
 */
public class CompilationFailedException extends ErrorConvertibleException {

  private static final String code = "Error occured during compile time";

  private final Throwable cause;

  public CompilationFailedException(Throwable cause) {
    this.cause = cause;
  }

  @Override
  public Error toError() {
    return new Error(code, cause.getMessage());
  }
}
