package io.infectnet.server.controller.websocket.exception;

import io.infectnet.server.controller.error.Error;
import io.infectnet.server.controller.error.ErrorConvertibleException;

public class AuthenticationFailedException extends ErrorConvertibleException{

    private final String code = "Authentication failed";

    private final String username;

    public AuthenticationFailedException (String username, Throwable cause){
        super(cause);
        this.username = username;
    }

    @Override
    public Error toError() {
        return new Error(code,username);
    }
}
