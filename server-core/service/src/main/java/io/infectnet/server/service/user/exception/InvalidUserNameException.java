package io.infectnet.server.service.user.exception;

public class InvalidUserNameException extends ValidationException {

    private static final String messageKey = "InvalidUserName";

    private static final String target = "username";

    public InvalidUserNameException() {
        super(messageKey, target);
    }
}
