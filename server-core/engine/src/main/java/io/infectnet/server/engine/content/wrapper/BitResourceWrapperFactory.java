package io.infectnet.server.engine.content.wrapper;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.BiConsumer;

public class BitResourceWrapperFactory implements EntityWrapperFactory<BitResourceWrapper> {

  private final BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer;

  public BitResourceWrapperFactory(BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    this.actionConsumer = actionConsumer;
  }

  @Override
  public BitResourceWrapper wrapEntity(Entity entity) {
    return new BitResourceWrapper(entity, this.actionConsumer);
  }

}
