package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.type.BitResourceTypeComponent;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.player.storage.PlayerStorageService;
import io.infectnet.server.engine.core.world.World;
import io.infectnet.server.engine.core.world.customizer.NestCustomizer;

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
  public static Function<Player, Player> providesIdentityPlayerInitializer(
      PlayerStorageService playerStorageService, EntityManager entityManager,
      World world, TypeRepository typeRepository, NestCustomizer nestCustomizer) {
    return (player) -> {
      playerStorageService.addStorageForPlayer(player);

      playerStorageService.getStorageForPlayer(player).ifPresent(storage -> {
        storage.setAttribute(BitResourceTypeComponent.TYPE_NAME, 50);
      });

      Optional<TypeComponent> typeComponent =
          typeRepository.getTypeByName(BitResourceTypeComponent.TYPE_NAME);

      if(typeComponent.isPresent()){
        Entity nest = typeComponent.get().createEntityOfType();

        nestCustomizer.getRandomNestPosition().ifPresent(pos -> {
          world.getTileByPosition(pos).setEntity(nest);

          entityManager.addEntity(nest);
        });
      }

      return player;
    };
  }

  @Provides
  @IntoSet
  public static Runnable providesEnvironmentPlayerRunnable(PlayerService playerService) {
    return () -> {
      playerService.createPlayer("Environment");
    };
  }
}
