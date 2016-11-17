package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.selector.OwnSelectorFactory;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.script.selector.Selector;
import io.infectnet.server.engine.core.script.selector.SelectorFactory;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SelectorModule {
  @Provides
  @IntoSet
  public static SelectorFactory<? extends Selector> providesOwnSelectorFactory(
      EntityManager entityManager, EntityWrapperRepository entityWrapperRepository) {
    return new OwnSelectorFactory(entityManager, entityWrapperRepository);
  }

}
