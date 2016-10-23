package io.infectnet.server.controller.exception;

import static spark.Spark.exception;

import com.google.gson.Gson;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.error.ErrorConvertibleException;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

/**
 * {@code ExceptionMapperController} provides exception handling using the
 * {@link spark.Spark#exception(Class, ExceptionHandler)} method. Not a true controller in the
 * sense, that it does not expose endpoints but only mappings.
 */
public class ExceptionMapperController implements RestController {
  private final Gson gson;

  /**
   * Constructs a new instance that uses the given {@code Gson} instance. Sadly
   * {@link spark.Spark#exception(Class, ExceptionHandler)} does not make it possible to use
   * {@link spark.ResponseTransformer}s, therefore we have to inject a {@code Gson} instance and
   * used it by hand.
   * @param gson the {@code Gson} instance
   */
  public ExceptionMapperController(Gson gson) {
    this.gson = gson;
  }

  @Override
  public void configure() {
    exception(ErrorConvertibleException.class, this::mapErrorConvertibleException);
  }

  private void mapErrorConvertibleException(Exception e, Request res, Response resp) {
    // Safe because we mapped this method to the ErrorConvertibleException.class
    ErrorConvertibleException errorConvertibleException = (ErrorConvertibleException)e;

    resp.header("Content-Type", "application/json");

    resp.status(400);

    resp.body(gson.toJson(errorConvertibleException.toError()));
  }
}
