package io.infectnet.server.engine.content.traits

import io.infectnet.server.engine.content.system.infect.InfectAction
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper


trait InfectTrait {

  void infect(EntityWrapper resource) {
    this.actionConsumer.accept(this.state, new InfectAction(this.wrappedEntity, resource.wrappedEntity));
  }

}
