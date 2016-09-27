package io.infectnet.server.core;

import dagger.Component;
import io.infectnet.server.controller.configuration.ControllerModule;

import javax.inject.Singleton;

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
