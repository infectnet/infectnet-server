package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.world.customizer.NestCustomizer;
import io.infectnet.server.engine.content.world.customizer.ResourceCustomizer;
import io.infectnet.server.engine.core.entity.EntityCreator;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.util.hook.Hook;
import io.infectnet.server.engine.core.util.hook.PostSetupHook;
import io.infectnet.server.engine.core.world.World;
import io.infectnet.server.engine.core.world.customizer.WorldCustomizer;

import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class WorldModule {
  @Provides
  @Singleton
  public static ResourceCustomizer providesResourceCustomizer(TypeRepository typeRepository,
                                                              PlayerService playerService,
                                                              EntityCreator entityCreator) {
    return new ResourceCustomizer(typeRepository, playerService, entityCreator);
  }

  @Provides
  @PostSetupHook
  @IntoSet
  public static Hook providesResourceCustomizerHook(ResourceCustomizer resourceCustomizer,
                                                    World world) {
    return Hook.from(10, () -> resourceCustomizer.customize(world));
  }

  @Provides
  @Singleton
  public static NestCustomizer providesNestCustomizer() {
    return new NestCustomizer();
  }

  @Provides
  @PostSetupHook
  @IntoSet
  public static Hook providesNestCustomizerHook(NestCustomizer nestCustomizer, World world) {
    return Hook.from(20, () -> nestCustomizer.customize(world));
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
