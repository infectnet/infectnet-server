package io.infectnet.server.engine.content.system.movement;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;

public class MovementAction extends Action {

  private final Entity targetEntity;

  public MovementAction(Entity movingEntity, Entity targetEntity) {
    super(movingEntity);

    this.targetEntity = targetEntity;
  }

  public Entity getTargetEntity() {
    return targetEntity;
  }
}
