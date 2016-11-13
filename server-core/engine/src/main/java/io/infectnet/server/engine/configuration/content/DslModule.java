package io.infectnet.server.engine.configuration.content;

import io.infectnet.server.engine.script.dsl.CollectBlock;
import io.infectnet.server.engine.script.dsl.DslBindingCustomizer;
import io.infectnet.server.engine.script.dsl.SelectFilterActionBlock;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class DslModule {
  @Provides
  @Singleton
  @IntoSet
  public static DslBindingCustomizer providesCollectBlock() {
    return new CollectBlock();
  }

  @Provides
  @Singleton
  @IntoSet
  public static DslBindingCustomizer providesSelectFilterActionBlock() {
    return new SelectFilterActionBlock();
  }
}
