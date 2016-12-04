package io.infectnet.server.engine.content.traits

import groovy.transform.SelfType
import io.infectnet.server.engine.content.system.hijack.HijackAction
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

@SelfType(EntityWrapper.class)
trait HijackTrait {

  void hijack(EntityWrapper targetEntity) {
    this.actionConsumer.accept(this.state, new HijackAction(this.wrappedEntity, Objects.requireNonNull(targetEntity).wrappedEntity));
  }

}
