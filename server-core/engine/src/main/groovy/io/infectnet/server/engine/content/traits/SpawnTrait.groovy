package io.infectnet.server.engine.content.traits

import groovy.transform.SelfType
import io.infectnet.server.engine.content.system.spawn.SpawnAction
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

@SelfType(EntityWrapper.class)
trait SpawnTrait {

  void spawn(String entityType) {
    this.actionConsumer.accept(this.state, new SpawnAction(this.wrappedEntity, Objects.requireNonNull(entityType)));
  }

}
