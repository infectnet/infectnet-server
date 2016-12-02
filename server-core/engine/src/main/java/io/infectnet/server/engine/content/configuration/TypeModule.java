package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.type.BitResourceTypeComponent;
import io.infectnet.server.engine.content.type.NestTypeComponent;
import io.infectnet.server.engine.content.type.RootkitTypeComponent;
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

  @Provides
  @IntoSet
  public static TypeComponent providesNestTypeComponent() {
    return new NestTypeComponent();
  }

  @Provides
  @IntoSet
  public static TypeComponent providesRootkitTypeComponent() {
    return new RootkitTypeComponent();
  }

  @Provides
  @IntoSet
  public static TypeComponent providesBitResourceTypeComponent() {
    return new BitResourceTypeComponent();
  }
}
