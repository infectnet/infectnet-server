package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.type.TrojanTypeComponent;
import io.infectnet.server.engine.content.type.WormTypeComponent;
import io.infectnet.server.engine.core.entity.component.TypeComponent;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class TypeModule {

  @Provides
  @IntoSet
  public static TypeComponent providesWormTypeComponent() {
    return new WormTypeComponent();
  }

  @Provides
  @IntoSet
  public static TypeComponent providesTrojanTypeComponent() {
    return new TrojanTypeComponent();
  }
}
