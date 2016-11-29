package io.infectnet.server.engine.content.traits

import io.infectnet.server.engine.content.system.hijack.HijackAction
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

trait HijackTrait {

  void hijack(EntityWrapper targetEntity) {
    this.actionConsumer.accept(this.state, new HijackAction(this.wrappedEntity, targetEntity.wrappedEntity));
  }

}
