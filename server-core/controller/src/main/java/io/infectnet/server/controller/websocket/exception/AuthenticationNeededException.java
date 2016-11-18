package io.infectnet.server.controller.websocket.exception;

import io.infectnet.server.controller.utils.error.Error;
import io.infectnet.server.controller.utils.error.ErrorConvertibleException;

/**
 * Exception thrown when a WebSocket controller requires authenticated session and the session is
 * not authenticated.
 */
public class AuthenticationNeededException extends ErrorConvertibleException {

  private static final String code = "Authentication failed";

  @Override
  public Error toError() {
    return new Error(code, null);
  }

}
