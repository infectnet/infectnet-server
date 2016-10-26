package io.infectnet.server.service.user.exception;

public class InvalidPasswordException extends ValidationException {

    private static final String messageKey = "Invalid password";

    private static final String target = "password";

    public InvalidPasswordException() {
        super(messageKey, target);
    }
}
