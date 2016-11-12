package io.infectnet.server.engine;

import io.infectnet.server.engine.configuration.EngineModule;

import javax.inject.Singleton;
import dagger.Component;

public class Engine {

  @Singleton
  @Component(modules = { EngineModule.class })
  interface Bootstrapper {
    EngineConfigurator getEngineConfigurator();
  }

  public static Engine create() {
    return new Engine();
  }

  /**
   * Cannot be instantiated directly.
   */
  private Engine() {
    EngineConfigurator configurator = DaggerEngine_Bootstrapper.create().getEngineConfigurator();

    configurator.configure();
  }
}
