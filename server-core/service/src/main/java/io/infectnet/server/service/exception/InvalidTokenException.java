package io.infectnet.server.service.exception;

public class InvalidTokenException extends ValidationException{

    private static final String messageKey = "InvalidToken";

    private static final String target = "token";

    public InvalidTokenException() {
        super(messageKey, target);
    }
}
