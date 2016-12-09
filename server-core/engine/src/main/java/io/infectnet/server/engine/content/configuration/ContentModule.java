package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.player.Environment;
import io.infectnet.server.engine.content.status.SynchronousStatusPublisher;
import io.infectnet.server.engine.content.type.BitResourceTypeComponent;
import io.infectnet.server.engine.content.type.NestTypeComponent;
import io.infectnet.server.engine.content.world.customizer.NestCustomizer;
import io.infectnet.server.engine.core.entity.EntityCreator;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.player.storage.PlayerStorageService;
import io.infectnet.server.engine.core.status.StatusPublisher;
import io.infectnet.server.engine.core.util.hook.Hook;
import io.infectnet.server.engine.core.util.hook.PostSetupHook;
import io.infectnet.server.engine.core.world.World;

import java.util.Optional;
import java.util.function.Function;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module(includes = {SelectorModule.class, DslModule.class, SystemModule.class, TypeModule.class,
    WorldModule.class, WrapperModule.class})
public class ContentModule {
  private static final String ENVIRONMENT_PLAYER = "Environment";

  @Provides
  @Singleton
  public static Function<Player, Player> providesDefaultPlayerInitializer(
      PlayerStorageService playerStorageService, TypeRepository typeRepository,
      NestCustomizer nestCustomizer, EntityCreator entityCreator) {
    return (player) -> {
      playerStorageService.addStorageForPlayer(player);

      playerStorageService.getStorageForPlayer(player).ifPresent(storage -> {
        storage.setAttribute(BitResourceTypeComponent.TYPE_NAME, 50);
      });

      Optional<TypeComponent> nestType = typeRepository.getTypeByName(NestTypeComponent.TYPE_NAME);

      if (nestType.isPresent()) {
        nestCustomizer.getRandomNestPosition().ifPresent(pos -> {
          entityCreator.create(nestType.get(), pos, player);
        });
      }

      return player;
    };
  }

  @Provides
  @PostSetupHook
  @IntoSet
  public static Hook providesEnvironmentPlayerInitializationHook(PlayerService playerService) {
    /*
     * Passing the identity function as the initializer so the default initialization function
     * will not be executed for the Environment player.
     */
    return Hook.from(0, () -> playerService.createPlayer(ENVIRONMENT_PLAYER, Function.identity()));
  }

  @Provides
  @Singleton
  @Environment
  public static Player providesEnvironmentPlayer(PlayerService playerService) {
    /*
     * If it throws, the whole application should just die.
     */
    return playerService.getPlayerByUsername(ENVIRONMENT_PLAYER).get();
  }


  @Provides
  @Singleton
  public static StatusPublisher providesStatusPublisher(PlayerService playerService,
                                                        EntityManager entityManager,
                                                        World world) {
    return new SynchronousStatusPublisher(playerService, entityManager, world);
  }
}
