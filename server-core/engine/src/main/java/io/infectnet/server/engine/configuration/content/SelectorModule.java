package io.infectnet.server.engine.configuration.content;

import io.infectnet.server.engine.entity.EntityManager;
import io.infectnet.server.engine.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.script.selector.Selector;
import io.infectnet.server.engine.script.selector.SelectorFactory;

import java.util.Collections;
import java.util.Set;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;

@Module
public class SelectorModule {
  @Provides
  @ElementsIntoSet
  public static Set<SelectorFactory<? extends Selector>> providesEmptySelectorSet() {
    return Collections.emptySet();
  }
}
