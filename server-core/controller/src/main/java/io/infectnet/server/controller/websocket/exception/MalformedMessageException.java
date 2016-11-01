package io.infectnet.server.controller.websocket.exception;

import io.infectnet.server.controller.error.Error;
import io.infectnet.server.controller.error.ErrorConvertibleException;

public class MalformedMessageException extends ErrorConvertibleException {

    private final String code;

    public MalformedMessageException(String message, Throwable cause) {
        super(cause);
        code = message;
    }

    @Override
    public Error toError() {
        return new Error(code,"message");
    }
}
