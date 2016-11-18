package io.infectnet.server.controller.websocket.game;

import io.infectnet.server.controller.error.Error;
import io.infectnet.server.controller.error.ErrorConvertibleException;

/**
 * Exception thrown when code uploaded by the user has syntax errors and we notify the user.
 */
public class CompileFailedException extends ErrorConvertibleException {

  private static final String code = "Error occured during compile time";

  private final Throwable cause;

  public CompileFailedException(Throwable cause) {
    this.cause = cause;
  }

  @Override
  public Error toError() {
    return new Error(code, cause.getMessage());
  }
}
