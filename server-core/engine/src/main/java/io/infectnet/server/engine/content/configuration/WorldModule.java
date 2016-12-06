package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.world.customizer.NestCustomizer;
import io.infectnet.server.engine.core.world.customizer.ResourceCustomizer;
import io.infectnet.server.engine.core.world.customizer.WorldCustomizer;

import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class WorldModule {
  @Provides
  @IntoSet
  public static WorldCustomizer providesResourceCustomizer(TypeRepository typeRepository) {
    return new ResourceCustomizer(typeRepository);
  }

  @Provides
  @IntoSet
  @Singleton
  public static WorldCustomizer providesNestCustomizer() {
    return new NestCustomizer();
  }
}
