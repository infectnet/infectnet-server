package io.infectnet.server.controller.rest.admin;

import io.infectnet.server.controller.utils.error.Error;
import io.infectnet.server.controller.utils.error.ErrorConvertibleException;

/**
 * This kind of exception is thrown when there's no token in a request sent to an administrator
 * endpoint.
 */
public class MissingTokenException extends ErrorConvertibleException {
  private static final String MESSAGE_KEY = "Missing token";

  private final String path;

  public MissingTokenException(String path) {
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
