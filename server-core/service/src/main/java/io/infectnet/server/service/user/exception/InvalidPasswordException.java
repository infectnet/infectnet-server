package io.infectnet.server.service.user.exception;

public class InvalidPasswordException extends ValidationException {

    private static final String messageKey = "InvalidPassword";

    private static final String target = "password";

    public InvalidPasswordException() {
        super(messageKey, target);
    }
}
