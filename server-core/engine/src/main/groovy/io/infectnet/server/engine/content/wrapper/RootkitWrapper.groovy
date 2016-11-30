package io.infectnet.server.engine.content.wrapper

import io.infectnet.server.engine.core.entity.Entity
import io.infectnet.server.engine.core.entity.wrapper.Action
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper

import java.util.function.BiConsumer


class RootkitWrapper extends EntityWrapper {

  RootkitWrapper(Entity wrappedEntity, BiConsumer<io.infectnet.server.engine.core.entity.wrapper.EntityWrapper.WrapperState, Action> actionConsumer) {
    super(wrappedEntity, actionConsumer)
  }

}
