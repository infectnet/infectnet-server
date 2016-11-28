package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.type.WormTypeComponent;
import io.infectnet.server.engine.content.wrapper.WormWrapperFactory;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.BiConsumer;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class WrapperModule {

  @Provides
  @IntoMap
  @StringKey(WormTypeComponent.TYPE_NAME)
  public static EntityWrapperFactory<? extends EntityWrapper> providesWorkerWrapperFactory(
      BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    return new WormWrapperFactory(actionConsumer);
  }
}
