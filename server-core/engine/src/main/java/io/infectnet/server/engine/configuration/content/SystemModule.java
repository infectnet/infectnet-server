package io.infectnet.server.engine.configuration.content;

import io.infectnet.server.engine.system.ProcessorSystem;

import java.util.Collections;
import java.util.Set;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;

@Module
public class SystemModule {
  @Provides
  @ElementsIntoSet
  public static Set<ProcessorSystem> providesEmptyProcessorSystemSet() {
    return Collections.emptySet();
  }
}
