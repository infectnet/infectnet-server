package io.infectnet.server.controller.engine;

import io.infectnet.server.common.configuration.ConfigurationHolder;
import io.infectnet.server.engine.Engine;

public class EngineConnectorImpl implements EngineConnector {

  private static final String DESIRED_TICK_DURATION_PROPERTY = "desired_tick_duration";

  private final Engine engine;

  public EngineConnectorImpl() {
    this.engine = Engine.create();
  }

  @Override
  public void start() {
    Long desiredTickDuration = Long.parseLong(
        ConfigurationHolder.INSTANCE.getActiveConfiguration().get(DESIRED_TICK_DURATION_PROPERTY));

    engine.start(desiredTickDuration);
  }

  @Override
  public boolean stop(StopType stopType) {
    switch (stopType) {
      case BLOCKING:
        return engine.stopBlocking();
      case ASYNC:
        return engine.stopAsync();
      default:
        return false;
    }
  }

}
