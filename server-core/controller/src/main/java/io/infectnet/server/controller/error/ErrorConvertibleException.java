package io.infectnet.server.controller.error;

/**
 * Abstract exception class that provides a method to be converted to an {@link Error} object.
 */
public abstract class ErrorConvertibleException extends Exception {
  public ErrorConvertibleException() {
  }

  public ErrorConvertibleException(String message) {
    super(message);
  }

  public ErrorConvertibleException(String message, Throwable cause) {
    super(message, cause);
  }

  public ErrorConvertibleException(Throwable cause) {
    super(cause);
  }

  /**
   * Converts the instance to an {@code Error} object.
   * @return the representation of this instance as an {@code Error}
   */
  public abstract Error toError();
}
