package io.infectnet.server.engine.content.wrapper

import io.infectnet.server.engine.content.print.PrintAction
import io.infectnet.server.engine.core.entity.Entity
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

import java.util.function.BiConsumer

class WorkerWrapper extends EntityWrapper {
  WorkerWrapper(Entity wrappedEntity, BiConsumer actionConsumer) {
    super(wrappedEntity, actionConsumer)
  }

  void print(String message) {
    actionConsumer.accept(this.state, new PrintAction(this.wrappedEntity, message));
  }
}
