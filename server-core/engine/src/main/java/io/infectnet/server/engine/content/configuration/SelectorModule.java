package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.dsl.DslBindingCustomizer;
import io.infectnet.server.engine.content.player.Environment;
import io.infectnet.server.engine.content.selector.EnemySelectorFactory;
import io.infectnet.server.engine.content.selector.EnvironmentSelectorFactory;
import io.infectnet.server.engine.content.selector.OwnSelectorFactory;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.world.World;

import javax.inject.Provider;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SelectorModule {

  @Provides
  @IntoSet
  public static DslBindingCustomizer providesOwnSelectorFactory(
      EntityManager entityManager, EntityWrapperRepository wrapperRepository) {
    return new OwnSelectorFactory(entityManager, wrapperRepository);
  }

  @Provides
  @IntoSet
  public static DslBindingCustomizer providesEnemySelectorFactory(
      EntityManager entityManager, EntityWrapperRepository wrapperRepository,
      PlayerService playerService, World world) {
    return new EnemySelectorFactory(entityManager, wrapperRepository, playerService, world);
  }

  @Provides
  @IntoSet
  public static DslBindingCustomizer providesEnvironmentSelectorFactory(
      EntityManager entityManager, EntityWrapperRepository wrapperRepository,
      @Environment Provider<Player> environmentPlayer, World world) {
    return new EnvironmentSelectorFactory(entityManager, wrapperRepository, environmentPlayer, world);
  }
}
