package io.infectnet.server.controller.admin;

import io.infectnet.server.controller.error.Error;
import io.infectnet.server.controller.error.ErrorConvertibleException;

public class UnauthorizedTokenException extends ErrorConvertibleException {
  private static final String MESSAGE_KEY = "Unauthorized token";

  private final String path;

  public UnauthorizedTokenException(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  @Override
  public Error toError() {
    return new Error(MESSAGE_KEY, path);
  }
}
