package io.infectnet.server.service.user.exception;

public class InvalidUserNameException extends ValidationException {

    private static final String messageKey = "Invalid username";

    private static final String target = "username";

    public InvalidUserNameException() {
        super(messageKey, target);
    }
}
