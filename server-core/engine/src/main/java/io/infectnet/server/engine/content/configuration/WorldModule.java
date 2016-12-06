package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.world.customizer.NestCustomizer;
import io.infectnet.server.engine.content.world.customizer.ResourceCustomizer;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.world.customizer.WorldCustomizer;

import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class WorldModule {
  @Provides
  @Singleton
  public static ResourceCustomizer providesResourceCustomizer(TypeRepository typeRepository,
                                                              PlayerService playerService) {
    return new ResourceCustomizer(typeRepository, playerService);
  }

  @Provides
  @Singleton
  public static NestCustomizer providesNestCustomizer() {
    return new NestCustomizer();
  }

  @Provides
  @Singleton
  public static List<WorldCustomizer> providesOrderedWorldCustomizerList(
      ResourceCustomizer resourceCustomizer,
      NestCustomizer nestCustomizer) {

    return Arrays.asList(
        resourceCustomizer,
        nestCustomizer
    );
  }
}
