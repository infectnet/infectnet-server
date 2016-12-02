package io.infectnet.server.engine.content.traits

import io.infectnet.server.engine.content.system.spawn.SpawnAction

trait SpawnTrait {

  void spawn(String entityType) {
    this.actionConsumer.accept(this.state, new SpawnAction(this.wrappedEntity, Objects.requireNonNull(entityType)));
  }

}
