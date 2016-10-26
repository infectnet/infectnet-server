package io.infectnet.server.controller.info;

import io.infectnet.server.common.VersionHolder;

public class InfoView {

  private final Fingerprint fingerprint = new Fingerprint();

  private class Fingerprint {

    private final String name = "InfectNet";

    private final String version = VersionHolder.VERSION_NUMBER;

  }

}
