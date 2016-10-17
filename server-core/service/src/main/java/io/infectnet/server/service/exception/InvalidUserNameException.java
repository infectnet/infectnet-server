package io.infectnet.server.service.exception;

public class InvalidUserNameException extends ValidationException{

    private static final String messageKey = "InvalidUserNameException";

    private static final String target = "username";

    public InvalidUserNameException() {
        super(messageKey, target);
    }
}
