package io.infectnet.server.service.user.exception;

public class ValidationException extends Exception {

    private ValidationException nextException;

    private final String target;

    public ValidationException(String message, String target) {
        super(message);
        this.target = target;
    }

    public ValidationException getNextException() {
        return nextException;
    }

    public void setNextException(ValidationException nextException) {
        this.nextException = nextException;
    }

    public String getTarget() {
        return target;
    }
}
