package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.dsl.GatherBlock;
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
  public static DslBindingCustomizer providesGatherBlock() {
    return new GatherBlock();
  }

  @Provides
  @Singleton
  @IntoSet
  public static DslBindingCustomizer providesSelectFilterActionBlock() {
    return new SelectFilterActionBlock();
  }
}
