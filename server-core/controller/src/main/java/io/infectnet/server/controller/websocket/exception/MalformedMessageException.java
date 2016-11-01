package io.infectnet.server.controller.websocket.exception;

import io.infectnet.server.controller.error.Error;
import io.infectnet.server.controller.error.ErrorConvertibleException;

public class MalformedMessageException extends ErrorConvertibleException {

    private final String code = "Malformed message";

    public MalformedMessageException(Throwable cause) {
        super(cause);
    }

    @Override
    public Error toError() {
        return new Error(code,"message");
    }
}
