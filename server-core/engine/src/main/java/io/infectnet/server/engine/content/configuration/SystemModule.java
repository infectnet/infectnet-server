package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.print.PrintSystem;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SystemModule {
  @Provides
  @IntoSet
  public static ProcessorSystem providesPrintSystem(
      @Named("Request Queue") ListenableQueue<Request> requestQueue) {
    return new PrintSystem(requestQueue);
  }

}
