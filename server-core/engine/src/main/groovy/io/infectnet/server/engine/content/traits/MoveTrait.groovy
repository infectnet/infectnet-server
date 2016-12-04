package io.infectnet.server.engine.content.traits

import groovy.transform.SelfType
import io.infectnet.server.engine.content.system.movement.MovementAction
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

@SelfType(EntityWrapper)
trait MoveTrait {

  void moveTo(EntityWrapper target) {
    this.actionConsumer.accept(this.state, new MovementAction(this.wrappedEntity, Objects.requireNonNull(target).wrappedEntity));
  }

}
