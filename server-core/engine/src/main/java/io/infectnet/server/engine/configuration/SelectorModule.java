package io.infectnet.server.engine.configuration;

import io.infectnet.server.engine.entity.EntityManager;
import io.infectnet.server.engine.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.script.selector.OwnSelectorFactory;
import io.infectnet.server.engine.script.selector.Selector;
import io.infectnet.server.engine.script.selector.SelectorFactory;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SelectorModule {
  @Provides
  @Singleton
  @IntoSet
  public static SelectorFactory<? extends Selector> providesOwnSelector(EntityManager entityManager,
    EntityWrapperRepository entityWrapperRepository) {
    return new OwnSelectorFactory(entityManager, entityWrapperRepository);
  }
}
