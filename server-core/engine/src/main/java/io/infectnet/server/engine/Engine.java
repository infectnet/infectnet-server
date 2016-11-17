package io.infectnet.server.engine;

import io.infectnet.server.engine.content.configuration.ContentModule;
import io.infectnet.server.engine.core.GameLoop;
import io.infectnet.server.engine.core.configuration.CoreModule;
import io.infectnet.server.engine.core.script.code.CodeRepository;

import javax.inject.Singleton;
import dagger.Component;

public class Engine {

  @Singleton
  @Component(modules = {CoreModule.class, ContentModule.class})
  interface Bootstrapper {
    EngineConfigurator getEngineConfigurator();

    GameLoop getGameLoop();

    CodeRepository getCodeRepository();
  }

  private final Bootstrapper bootstrapper;

  public static Engine create() {
    return new Engine();
  }

  /**
   * Cannot be instantiated directly.
   */
  private Engine() {
    this.bootstrapper = DaggerEngine_Bootstrapper.create();

    EngineConfigurator configurator = DaggerEngine_Bootstrapper.create().getEngineConfigurator();

    configurator.configure();
  }

  public void start(long desiredTickDuration) {
    bootstrapper.getGameLoop().start(desiredTickDuration);
  }

  public boolean stopBlocking() {
    return bootstrapper.getGameLoop().stopAndWait();
  }

  public boolean stopAsync() {
    bootstrapper.getGameLoop().stop();
    return true;
  }
}
