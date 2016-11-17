package io.infectnet.server.controller.configuration;

import io.infectnet.server.controller.engine.EngineConnector;
import io.infectnet.server.controller.engine.EngineConnectorImpl;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class EngineModule {
  @Provides
  @Singleton
  public static EngineConnector providesEngineConnector() {
    return new EngineConnectorImpl();
  }

}
