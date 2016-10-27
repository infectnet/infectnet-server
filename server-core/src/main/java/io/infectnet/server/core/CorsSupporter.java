package io.infectnet.server.core;

import static spark.Spark.before;
import static spark.Spark.options;

/**
 * Responsible for CORS (cross-origin resource sharing) support.
 * Please note that this feature should only be enabled after WebSocket initialization.
 */
public class CorsSupporter {

  private static final String DEFAULT_ALLOWED_ORIGIN = "*";
  private static final String DEFAULT_ALLOWED_METHODS = "POST, GET, OPTIONS, PUT, DELETE";
  private static final String DEFAULT_ALLOWED_HEADERS = "Content-Type, Accept, Authorization";

  private CorsSupporter() {
    /*
     * Do nothing...
     */
  }

  /**
   * Enables CORS (cross-origin resource sharing) on the server with default settings.
   * It is necessary to be enabled, otherwise clients can't connect to the server.
   */
  public static void enableCORS() {
    enableCORS(DEFAULT_ALLOWED_ORIGIN, DEFAULT_ALLOWED_METHODS, DEFAULT_ALLOWED_HEADERS);
  }

  /**
   * Enables CORS (cross-origin resource sharing) on the server with the given settings.
   * It is necessary to be enabled, otherwise clients can't connect to the server.
   * @param origin the allowed origins
   * @param methods the allowed methods
   * @param headers the allowed headers
   */
  public static void enableCORS(final String origin, final String methods, final String headers) {

    options("/*", (request, response) -> {

      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    before((request, response) -> {
      response.header("Access-Control-Allow-Origin", origin);
      response.header("Access-Control-Allow-Method", methods);
      response.header("Access-Control-Allow-Headers", headers);
    });
  }

}
