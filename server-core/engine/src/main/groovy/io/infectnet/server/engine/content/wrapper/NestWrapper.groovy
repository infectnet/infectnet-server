package io.infectnet.server.engine.content.wrapper

import io.infectnet.server.engine.content.traits.SpawnTrait
import io.infectnet.server.engine.core.entity.Entity
import io.infectnet.server.engine.core.entity.wrapper.Action
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

import java.util.function.BiConsumer


class NestWrapper extends EntityWrapper implements SpawnTrait {

  NestWrapper(Entity wrappedEntity, BiConsumer<io.infectnet.server.engine.core.entity.wrapper.EntityWrapper.WrapperState, Action> actionConsumer) {
    super(wrappedEntity, actionConsumer)
  }

}
