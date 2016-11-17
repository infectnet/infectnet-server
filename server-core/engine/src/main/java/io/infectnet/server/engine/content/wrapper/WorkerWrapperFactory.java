package io.infectnet.server.engine.content.wrapper;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;

import java.util.function.BiConsumer;

public class WorkerWrapperFactory implements EntityWrapperFactory<WorkerWrapper> {
  private final BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer;

  public WorkerWrapperFactory(BiConsumer<EntityWrapper.WrapperState, Action> actionConsumer) {
    this.actionConsumer = actionConsumer;
  }

  @Override
  public WorkerWrapper wrapEntity(Entity entity) {
    return new WorkerWrapper(entity, this.actionConsumer);
  }
}
