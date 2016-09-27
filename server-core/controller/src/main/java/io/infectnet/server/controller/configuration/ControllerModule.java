package io.infectnet.server.controller.configuration;

import dagger.Module;
import io.infectnet.server.service.configuration.ServiceModule;

@Module(includes = ServiceModule.class)
public class ControllerModule {

}
