package io.infectnet.server.service.exception;

public class InvalidEmailException extends ValidationException{

    private static final String messageKey = "InvalidEmail";

    private static final String target = "Email";

    public InvalidEmailException() {
        super(messageKey, target);
    }
}
