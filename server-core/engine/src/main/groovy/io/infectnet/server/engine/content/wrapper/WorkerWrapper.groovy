package io.infectnet.server.engine.content.wrapper

import io.infectnet.server.engine.content.print.PrintAction
import io.infectnet.server.engine.core.entity.Entity
import io.infectnet.server.engine.core.entity.wrapper.Action
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

import java.util.function.Consumer

class WorkerWrapper extends EntityWrapper {
  WorkerWrapper(Entity wrappedEntity, Consumer<Action> actionConsumer) {
    super(wrappedEntity, actionConsumer)
  }

  void print(String message) {
    actionConsumer.accept(new PrintAction(this.wrappedEntity, message));
  }
}
