package io.infectnet.server.engine.content.traits

import io.infectnet.server.engine.content.system.boot.BootAction

trait BootTrait {

  void boot(String buildingType) {
    this.actionConsumer.accept(this.state, new BootAction(this.wrappedEntity, buildingType));
  }

}
