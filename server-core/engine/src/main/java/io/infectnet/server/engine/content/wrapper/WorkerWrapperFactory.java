package io.infectnet.server.engine.content.wrapper;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;
import io.infectnet.server.engine.core.util.ListenableQueue;

import java.util.function.Consumer;

public class WorkerWrapperFactory implements EntityWrapperFactory<WorkerWrapper> {
  private final Consumer<Action> actionConsumer;

  public WorkerWrapperFactory(Consumer<Action> actionConsumer) {
    this.actionConsumer = actionConsumer;
  }

  @Override
  public WorkerWrapper wrapEntity(Entity entity) {
    return new WorkerWrapper(entity, this.actionConsumer);
  }
}
