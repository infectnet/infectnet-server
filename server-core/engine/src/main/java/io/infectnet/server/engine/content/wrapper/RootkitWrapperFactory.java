package io.infectnet.server.engine.content.wrapper;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.BiConsumer;

public class RootkitWrapperFactory implements EntityWrapperFactory<RootkitWrapper> {

  private final BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer;

  public RootkitWrapperFactory(BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    this.actionConsumer = actionConsumer;
  }

  @Override
  public RootkitWrapper wrapEntity(Entity entity) {
    return new RootkitWrapper(entity, this.actionConsumer);
  }
}
