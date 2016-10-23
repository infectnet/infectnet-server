package io.infectnet.server.controller.utils;

import org.apache.commons.lang3.StringUtils;

import spark.Response;

/**
 * Utility class exposing various response-related helper methods that contain frequently used
 * shared functionality among controllers.
 */
public final class ResponseUtils {
  private ResponseUtils() {
    /**
     * Prevent instantiation.
     */
  }

  /**
   * Returns an empty {@code String} and sets the response status code to {@code 200 OK}.
   * @param resp the response object
   * @return an empty String
   */
  public static String sendEmptyOk(Response resp) {
    return sendEmptyWithStatusCode(resp, 200);
  }

  /**
   * Returns an empty {@code String} and sets the response status code to the specified value.
   * @param resp the response object
   * @param statusCode the status code to be set on the response
   * @return an empty String
   */
  public static String sendEmptyWithStatusCode(Response resp, int statusCode) {
   resp.status(statusCode);

    return StringUtils.EMPTY;
  }
}
