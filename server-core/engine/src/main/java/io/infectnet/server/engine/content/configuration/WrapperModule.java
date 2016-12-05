package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.type.BitResourceTypeComponent;
import io.infectnet.server.engine.content.type.NestTypeComponent;
import io.infectnet.server.engine.content.type.RootkitTypeComponent;
import io.infectnet.server.engine.content.type.TrojanTypeComponent;
import io.infectnet.server.engine.content.type.WormTypeComponent;
import io.infectnet.server.engine.content.wrapper.BitResourceWrapperFactory;
import io.infectnet.server.engine.content.wrapper.NestWrapperFactory;
import io.infectnet.server.engine.content.wrapper.RootkitWrapperFactory;
import io.infectnet.server.engine.content.wrapper.TrojanWrapperFactory;
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
  public static EntityWrapperFactory<? extends EntityWrapper> providesWormWrapperFactory(
      BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    return new WormWrapperFactory(actionConsumer);
  }

  @Provides
  @IntoMap
  @StringKey(TrojanTypeComponent.TYPE_NAME)
  public static EntityWrapperFactory<? extends EntityWrapper> providesTrojanWrapperFactory(
      BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    return new TrojanWrapperFactory(actionConsumer);
  }

  @Provides
  @IntoMap
  @StringKey(NestTypeComponent.TYPE_NAME)
  public static EntityWrapperFactory<? extends EntityWrapper> providesNestWrapperFactory(
      BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    return new NestWrapperFactory(actionConsumer);
  }

  @Provides
  @IntoMap
  @StringKey(RootkitTypeComponent.TYPE_NAME)
  public static EntityWrapperFactory<? extends EntityWrapper> providesRootkitWrapperFactory(
      BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    return new RootkitWrapperFactory(actionConsumer);
  }

  @Provides
  @IntoMap
  @StringKey(BitResourceTypeComponent.TYPE_NAME)
  public static EntityWrapperFactory<? extends EntityWrapper> providesBitResourceWrapperFactory(
      BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    return new BitResourceWrapperFactory(actionConsumer);
  }
}
