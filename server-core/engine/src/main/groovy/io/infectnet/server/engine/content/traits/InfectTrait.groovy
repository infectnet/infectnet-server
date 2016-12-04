package io.infectnet.server.engine.content.traits

import groovy.transform.SelfType
import io.infectnet.server.engine.content.system.infect.InfectAction
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

@SelfType(EntityWrapper)
trait InfectTrait {

  void infect(EntityWrapper resource) {
    this.actionConsumer.accept(this.state, new InfectAction(this.wrappedEntity, Objects.requireNonNull(resource).wrappedEntity));
  }

}
