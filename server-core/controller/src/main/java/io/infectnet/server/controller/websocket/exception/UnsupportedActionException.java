package io.infectnet.server.controller.websocket.exception;

import io.infectnet.server.controller.error.Error;
import io.infectnet.server.controller.error.ErrorConvertibleException;
import io.infectnet.server.controller.websocket.messaging.Action;

public class UnsupportedActionException extends ErrorConvertibleException {
  private static final String code = "Unsupported action";

  private final Action action;

  public UnsupportedActionException(Action action) {
    this.action = action;
  }

  @Override
  public Error toError() {
    return new Error(code, action.toString());
  }

  public Action getAction() {
    return action;
  }
}
