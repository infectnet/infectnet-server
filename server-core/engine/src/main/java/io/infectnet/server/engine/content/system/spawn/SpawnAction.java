package io.infectnet.server.engine.content.system.spawn;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;

public class SpawnAction extends Action {

  private final String entityType;

  public SpawnAction(Entity source, String entityType) {
    super(source);

    this.entityType = entityType;
  }

  public String getEntityType() {
    return entityType;
  }
}
