package io.infectnet.server.engine.content.system.infect;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;


public class InfectAction extends Action {

  private final Entity resource;

  public InfectAction(Entity worker, Entity resource) {
    super(worker);

    this.resource = resource;
  }

  public Entity getResource() {
    return resource;
  }
}

