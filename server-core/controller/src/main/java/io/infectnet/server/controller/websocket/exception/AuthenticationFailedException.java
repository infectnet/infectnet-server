package io.infectnet.server.controller.websocket.exception;

import io.infectnet.server.controller.utils.error.Error;
import io.infectnet.server.controller.utils.error.ErrorConvertibleException;

public class AuthenticationFailedException extends ErrorConvertibleException {

  private final String code = "Authentication failed";

  private final String username;

  public AuthenticationFailedException(String username) {
    this.username = username;
  }

  @Override
  public Error toError() {
    return new Error(code, username);
  }

  public String getUsername() {
    return username;
  }
}
