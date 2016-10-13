package io.infectnet.server.core;

import io.infectnet.server.controller.configuration.ControllerModule;

import javax.inject.Singleton;
import dagger.Component;

public class Application {

  @Singleton
  @Component(modules = {ControllerModule.class})
  public interface Bootstrapper {
    ApplicationStarter getApplicationStarter();
  }

  public static void main(String[] args) {
    Bootstrapper bootstrapper = DaggerApplication_Bootstrapper.builder().build();
    bootstrapper.getApplicationStarter().start();

  }

}
