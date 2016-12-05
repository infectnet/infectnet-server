package io.infectnet.server.controller.rest.user;

import io.infectnet.server.controller.utils.error.Error;
import io.infectnet.server.controller.utils.error.ErrorConvertibleException;
import io.infectnet.server.service.user.exception.ValidationException;

/**
 * Exception that's thrown when registration fails.
 */
public class RegistrationFailedException extends ErrorConvertibleException {
  private static final String MESSAGE_KEY = "Registration failed";

  private static final String TARGET = "registration";

  private final ValidationException innerException;

  /**
   * Constructs a new instance that wraps the specified {@code ValidationException} that caused the
   * registration failure. This inner exception can be accessed either with the
   * {@link #getInnerException()} or with the {@link #getCause()} method.
   * @param innerException the wrapped {@code Exception}
   */
  public RegistrationFailedException(ValidationException innerException) {
    super(MESSAGE_KEY, innerException);

    this.innerException = innerException;
  }

  /**
   * Gets the wrapped inner exception that caused the registration to fail.
   * @return the wrapped inner exception
   */
  public ValidationException getInnerException() {
    return innerException;
  }

  @Override
  public Error toError() {
    Error error = new Error(MESSAGE_KEY, TARGET);

    ValidationException e = innerException;

    do {
      error.addDetail(convertValidationExceptionToError(e));

      e = e.getNextException();
    } while (e != null);

    return error;
  }

  private Error convertValidationExceptionToError(ValidationException e) {
    return new Error(e.getMessage(), e.getTarget());
  }
}
