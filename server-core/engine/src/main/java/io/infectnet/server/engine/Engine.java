package io.infectnet.server.engine;

import io.infectnet.server.engine.configuration.EngineConfigurator;
import io.infectnet.server.engine.configuration.core.CoreModule;
import io.infectnet.server.engine.configuration.content.ContentModule;

import javax.inject.Singleton;
import dagger.Component;

public class Engine {

  @Singleton
  @Component(modules = { CoreModule.class, ContentModule.class })
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
