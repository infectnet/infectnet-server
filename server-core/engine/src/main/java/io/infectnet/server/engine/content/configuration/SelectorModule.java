package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.selector.EnemySelectorFactory;
import io.infectnet.server.engine.content.selector.OwnSelectorFactory;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.script.selector.Selector;
import io.infectnet.server.engine.core.script.selector.SelectorFactory;
import io.infectnet.server.engine.core.world.World;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SelectorModule {

  @Provides
  @IntoSet
  public static SelectorFactory<? extends Selector> providesOwnSelectorFactory(
      EntityManager entityManager, EntityWrapperRepository wrapperRepository) {
    return new OwnSelectorFactory(entityManager, wrapperRepository);
  }

  @Provides
  @IntoSet
  public static SelectorFactory<? extends Selector> providesEnemySelectorFactory(
      EntityManager entityManager, EntityWrapperRepository wrapperRepository,
      PlayerService playerService, World world) {
    return new EnemySelectorFactory(entityManager, wrapperRepository, playerService, world);
  }
}
