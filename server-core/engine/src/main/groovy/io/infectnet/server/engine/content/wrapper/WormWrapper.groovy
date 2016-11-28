package io.infectnet.server.engine.content.wrapper

import io.infectnet.server.engine.content.traits.InfectTrait
import io.infectnet.server.engine.content.traits.MoveTrait
import io.infectnet.server.engine.core.entity.Entity
import io.infectnet.server.engine.core.entity.wrapper.Action
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

import java.util.function.BiConsumer


class WormWrapper extends EntityWrapper implements InfectTrait, MoveTrait {

  WormWrapper(Entity wrappedEntity, BiConsumer<io.infectnet.server.engine.core.entity.wrapper.EntityWrapper.WrapperState, Action> actionConsumer) {
    super(wrappedEntity, actionConsumer)
  }

}
