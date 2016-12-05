package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.status.SynchronousStatusPublisher;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.status.StatusPublisher;
import io.infectnet.server.engine.core.world.World;

import java.util.function.Function;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module(includes = {SelectorModule.class, DslModule.class, SystemModule.class, TypeModule.class,
    WorldModule.class, WrapperModule.class})
public class ContentModule {
  @Provides
  @Singleton
  public static Function<Player, Player> providesIdentityPlayerInitializer() {
    return Function.identity();
  }

  @Provides
  @IntoSet
  public static Runnable providesEnvironmentPlayerRunnable(PlayerService playerService) {
    return () -> {
      playerService.createPlayer("Environment");
    };
  }

  @Provides
  @Singleton
  public static StatusPublisher providesStatusPublisher(PlayerService playerService,
                                                        EntityManager entityManager,
                                                        World world) {
    return new SynchronousStatusPublisher(playerService, entityManager, world);
  }
}
