package io.infectnet.server.engine.content.wrapper

import io.infectnet.server.engine.core.entity.Entity
import io.infectnet.server.engine.core.entity.wrapper.Action
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper
import io.infectnet.server.engine.core.traits.InventoryTrait

import java.util.function.BiConsumer


class BitResourceWrapper extends EntityWrapper implements InventoryTrait {

  BitResourceWrapper(Entity wrappedEntity, BiConsumer<io.infectnet.server.engine.core.entity.wrapper.EntityWrapper.WrapperState, Action> actionConsumer) {
    super(wrappedEntity, actionConsumer)
  }

}
