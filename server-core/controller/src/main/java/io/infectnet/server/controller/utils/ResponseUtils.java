package io.infectnet.server.controller.utils;

import org.apache.commons.lang3.StringUtils;

import spark.Response;

public final class ResponseUtils {
  private ResponseUtils() {
    /**
     * Prevent instantiation.
     */
  }

  public static String sendEmptyOk(Response resp) {
    return sendEmptyWithStatusCode(resp, 200);
  }

  public static String sendEmptyWithStatusCode(Response resp, int statusCode) {
   resp.status(statusCode);

    return StringUtils.EMPTY;
  }
}
