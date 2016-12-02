package io.infectnet.server.engine.content.system.movement;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.world.Position;

public class MovementRequest extends Request {

  private final Position position;

  public MovementRequest(Entity target,
                         Action origin,
                         Position position) {
    super(target, origin);

    this.position = position;
  }

  public Position getPosition() {
    return position;
  }
}
