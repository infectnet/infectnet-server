package io.infectnet.server.controller.engine;

public interface EngineConnector {

  void start();

  boolean stop(StopType stopType);

  enum StopType {
    BLOCKING,
    ASYNC
  }

}
