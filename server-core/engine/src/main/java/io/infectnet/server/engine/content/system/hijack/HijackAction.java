package io.infectnet.server.engine.content.system.hijack;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;


public class HijackAction extends Action {

  private final Entity targetEntity;

  public HijackAction(Entity source, Entity targetEntity) {
    super(source);

    this.targetEntity = targetEntity;
  }

  public Entity getTargetEntity() {
    return targetEntity;
  }
}
