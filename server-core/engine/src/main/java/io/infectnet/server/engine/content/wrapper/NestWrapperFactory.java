package io.infectnet.server.engine.content.wrapper;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.BiConsumer;

public class NestWrapperFactory implements EntityWrapperFactory<NestWrapper> {

  private final BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer;

  public NestWrapperFactory(BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    this.actionConsumer = actionConsumer;
  }

  @Override
  public NestWrapper wrapEntity(Entity entity) {
    return new NestWrapper(entity, this.actionConsumer);
  }

}
