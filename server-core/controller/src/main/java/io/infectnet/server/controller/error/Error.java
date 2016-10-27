package io.infectnet.server.controller.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generic error object that can be sent back to clients. Contains the error code which is an i18n
 * key, the target of the error that can be used for example to mark erroneous request fields and
 * error details which are inner {@code Error} objects. These inner errors can be used to describe
 * the background of the error in a more detailed (as the name suggests) way.
 */
public final class Error {
  private final String code;

  private final String target;

  private final List<Error> details;

  /**
   * Constructs a new instance with the specified code and target and an empty details list.
   * @param code the code that describes the error
   * @param target the target of the error (possibly a causing field, etc.)
   */
  public Error(String code, String target) {
    this.code = code;
    this.target = target;

    this.details = new ArrayList<>();
  }

  /**
   * Gets the code.
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Gets the target.
   * @return the target
   */
  public String getTarget() {
    return target;
  }

  /**
   * Gets an unmodifiable view of the details list.
   * @return the list of details
   */
  public List<Error> getDetails() {
    return Collections.unmodifiableList(details);
  }

  /**
   * Adds a new {@code Error} to the details list.
   * @param detail the detail to add
   */
  public void addDetail(Error detail) {
    details.add(detail);
  }
}
