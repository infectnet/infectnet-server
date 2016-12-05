package io.infectnet.server.engine.content.system.health;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;

public class HealthModificationRequest extends Request {

  private final int modificationNumber;

  public HealthModificationRequest(Entity target,
                                   Action origin,
                                   int modificationNumber) {
    super(target, origin);

    this.modificationNumber = modificationNumber;
  }

  public int getModificationNumber() {
    return modificationNumber;
  }
}
