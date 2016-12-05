package io.infectnet.server.engine.content.wrapper;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.BiConsumer;

public class TrojanWrapperFactory implements EntityWrapperFactory<TrojanWrapper> {

  private final BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer;

  public TrojanWrapperFactory(
      BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    this.actionConsumer = actionConsumer;
  }

  @Override
  public TrojanWrapper wrapEntity(Entity entity) {
    return new TrojanWrapper(entity, this.actionConsumer);
  }

}
