package io.infectnet.server.engine.content.system.boot;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;


public class BootRequest extends Request {

  private final TypeComponent buildingType;

  public BootRequest(Entity target, Action origin, TypeComponent buildingType) {
    super(target, origin);

    this.buildingType = buildingType;
  }

  public TypeComponent getBuildingType() {
    return buildingType;
  }
}
