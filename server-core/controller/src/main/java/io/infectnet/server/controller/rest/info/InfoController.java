package io.infectnet.server.controller.rest.info;

import com.google.gson.Gson;

import io.infectnet.server.common.VersionHolder;
import io.infectnet.server.controller.rest.RestController;

import spark.Request;
import spark.Response;
import spark.Spark;

public class InfoController implements RestController {

  private static final String URL_PATH = "/info";

  private final Gson gson;

  public InfoController(Gson gson) {
    this.gson = gson;
  }

  @Override
  public void configure() {
    Spark.get(URL_PATH, this::infoEndpoint, gson::toJson);

  }

  private Object infoEndpoint(Request req, Response resp) {
    return new InfoView();
  }

  private class InfoView {

    private final Fingerprint fingerprint = new Fingerprint();

    private class Fingerprint {

      private final String name = "InfectNet";

      private final String version = VersionHolder.VERSION_NUMBER;

    }

  }

}
