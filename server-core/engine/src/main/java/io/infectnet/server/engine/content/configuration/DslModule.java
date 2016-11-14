package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.dsl.CollectBlock;
import io.infectnet.server.engine.content.dsl.DslBindingCustomizer;
import io.infectnet.server.engine.content.dsl.SelectFilterActionBlock;

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
