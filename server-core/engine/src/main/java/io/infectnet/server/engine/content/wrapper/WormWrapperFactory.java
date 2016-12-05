package io.infectnet.server.engine.content.wrapper;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.BiConsumer;


public class WormWrapperFactory implements EntityWrapperFactory<WormWrapper> {

  private final BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer;

  public WormWrapperFactory(BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    this.actionConsumer = actionConsumer;
  }

  @Override
  public WormWrapper wrapEntity(Entity entity) {
    return new WormWrapper(entity, this.actionConsumer);
  }
}
