package io.infectnet.server.controller.admin;

import io.infectnet.server.controller.error.Error;
import io.infectnet.server.controller.error.ErrorConvertibleException;

/**
 * Exception that's thrown when an administrator login attempt fails.
 */
public class LoginFailedException extends ErrorConvertibleException {
  private static final String MESSAGE_KEY = "Login failed";

  private static final String TARGET = "login";

  @Override
  public Error toError() {
    return new Error(MESSAGE_KEY, TARGET);
  }
}
