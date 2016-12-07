package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.status.SynchronousStatusPublisher;
import io.infectnet.server.engine.content.type.BitResourceTypeComponent;
import io.infectnet.server.engine.content.type.NestTypeComponent;
import io.infectnet.server.engine.content.world.customizer.NestCustomizer;
import io.infectnet.server.engine.core.entity.Entity;
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
  @Provides
  @Singleton
  public static Function<Player, Player> providesDefaultPlayerInitializer(
      PlayerStorageService playerStorageService, EntityManager entityManager,
      World world, TypeRepository typeRepository,
      NestCustomizer nestCustomizer) {
    return (player) -> {
      playerStorageService.addStorageForPlayer(player);

      playerStorageService.getStorageForPlayer(player).ifPresent(storage -> {
        storage.setAttribute(BitResourceTypeComponent.TYPE_NAME, 50);
      });

      Optional<TypeComponent> typeComponent =
          typeRepository.getTypeByName(NestTypeComponent.TYPE_NAME);

      if (typeComponent.isPresent()) {
        Entity nest = typeComponent.get().createEntityOfType();

        nest.getOwnerComponent().setOwner(player);

        nestCustomizer.getRandomNestPosition().ifPresent(pos -> {
          nest.getPositionComponent().setPosition(pos);

          world.getTileByPosition(pos).setEntity(nest);

          entityManager.addEntity(nest);
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
    return Hook.from(0, () -> playerService.createPlayer("Environment", Function.identity()));
  }

  @Provides
  @Singleton
  public static StatusPublisher providesStatusPublisher(PlayerService playerService,
                                                        EntityManager entityManager,
                                                        World world) {
    return new SynchronousStatusPublisher(playerService, entityManager, world);
  }
}
