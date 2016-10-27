package io.infectnet.server.service.user.exception;

public class InvalidTokenException extends ValidationException {

    private static final String messageKey = "Invalid token";

    private static final String target = "token";

    public InvalidTokenException() {
        super(messageKey, target);
    }
}
