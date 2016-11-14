package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.core.system.ProcessorSystem;

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
