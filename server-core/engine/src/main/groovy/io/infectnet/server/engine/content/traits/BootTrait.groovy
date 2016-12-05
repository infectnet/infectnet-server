package io.infectnet.server.engine.content.traits

import groovy.transform.CompileStatic
import groovy.transform.SelfType
import io.infectnet.server.engine.content.system.boot.BootAction
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

@SelfType(EntityWrapper)
trait BootTrait {

  void boot(String buildingType) {
    this.actionConsumer.accept(this.state, new BootAction(this.wrappedEntity, Objects.requireNonNull(buildingType)));
  }

}
