package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.system.infect.InfectSystem;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.World;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SystemModule {

  @Provides
  @IntoSet
  public static ProcessorSystem providesInfectSystem(
      @Named("Request Queue") ListenableQueue<Request> requestQueue, World world) {
    return new InfectSystem(requestQueue, world);
  }
}
