package io.infectnet.server.service.user.exception;

public class InvalidEmailException extends ValidationException {

    private static final String messageKey = "Invalid email";

    private static final String target = "email";

    public InvalidEmailException() {
        super(messageKey, target);
    }
}
