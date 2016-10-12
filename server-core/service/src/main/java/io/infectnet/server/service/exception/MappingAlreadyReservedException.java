package io.infectnet.server.service.exception;

/**
 * Exception thrown, when a converter mapping is already reserved,
 * but we wanted to create a new one using it.
 */
public class MappingAlreadyReservedException extends RuntimeException {

    public MappingAlreadyReservedException() {
        super();
    }

    public MappingAlreadyReservedException(String message) {
        super(message);
    }

}
