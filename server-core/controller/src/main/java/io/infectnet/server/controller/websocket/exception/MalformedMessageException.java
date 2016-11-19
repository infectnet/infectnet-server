package io.infectnet.server.controller.websocket.exception;

import io.infectnet.server.controller.utils.error.Error;
import io.infectnet.server.controller.utils.error.ErrorConvertibleException;

public class MalformedMessageException extends ErrorConvertibleException {

  private final String code = "Malformed message";

  public MalformedMessageException(Throwable cause) {
    super(cause);
  }

  @Override
  public Error toError() {
    return new Error(code, "message");
  }
}
