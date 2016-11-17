package io.infectnet.server.core;

import io.infectnet.server.controller.configuration.ControllerModule;
import io.infectnet.server.controller.configuration.EngineModule;

import javax.inject.Singleton;
import dagger.Component;

public class Application {

  @Singleton
  @Component(modules = {ControllerModule.class, EngineModule.class})
  interface Bootstrapper {
    ApplicationStarter getApplicationStarter();
  }

  public static void main(String[] args) {
    Bootstrapper bootstrapper = DaggerApplication_Bootstrapper.create();
    bootstrapper.getApplicationStarter().start();

  }

}
