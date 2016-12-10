package io.infectnet.server.engine.content.system.movement;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.world.Position;

public class MovementAction extends Action {

  private final Position targetPosition;

  public MovementAction(Entity movingEntity, Position targetPosition) {
    super(movingEntity);

    this.targetPosition = targetPosition;
  }

  public Position getTargetPosition() {
    return targetPosition;
  }
}
