package io.infectnet.server.engine.content.system.kill;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;


public class KillRequest extends Request {

  public KillRequest(Entity target,
                     Action origin) {
    super(target, origin);
  }
}
