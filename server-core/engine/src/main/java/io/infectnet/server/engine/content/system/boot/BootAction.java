package io.infectnet.server.engine.content.system.boot;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;

public class BootAction extends Action {

  private final String buildingType;

  public BootAction(Entity source, String buildingType) {
    super(source);

    this.buildingType = buildingType;
  }

  public String getBuildingType() {
    return buildingType;
  }
}
