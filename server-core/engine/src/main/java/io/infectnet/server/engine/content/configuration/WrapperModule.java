package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.type.WorkerTypeComponent;
import io.infectnet.server.engine.content.wrapper.WorkerWrapperFactory;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.Consumer;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class WrapperModule {
  @Provides
  @IntoMap
  @StringKey(WorkerTypeComponent.TYPE_NAME)
  public static EntityWrapperFactory<? extends EntityWrapper> providesWorkerWrapperFactory(
      Consumer<Action> actionConsumer) {
    return new WorkerWrapperFactory(actionConsumer);
  }
}
