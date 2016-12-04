package io.infectnet.server.engine.content.wrapper

import io.infectnet.server.engine.content.traits.HijackTrait
import io.infectnet.server.engine.content.traits.MoveTrait
import io.infectnet.server.engine.core.entity.Entity
import io.infectnet.server.engine.core.entity.wrapper.Action
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper
import io.infectnet.server.engine.core.traits.InventoryTrait

import java.util.function.BiConsumer

class TrojanWrapper extends EntityWrapper implements MoveTrait, HijackTrait, InventoryTrait {

  TrojanWrapper(Entity wrappedEntity, BiConsumer<io.infectnet.server.engine.core.entity.wrapper.EntityWrapper.WrapperState, Action> actionConsumer) {
    super(wrappedEntity, actionConsumer)
  }

}
