package io.infectnet.server.engine.content.system.spawn;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;

public class SpawnRequest extends Request {

  private final TypeComponent entityType;

  public SpawnRequest(Entity target, Action origin, TypeComponent entityType) {
    super(target, origin);

    this.entityType = entityType;
  }

  public TypeComponent getEntityType() {
    return entityType;
  }
}
