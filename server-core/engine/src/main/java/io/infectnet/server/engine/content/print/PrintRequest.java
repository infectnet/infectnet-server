package io.infectnet.server.engine.content.print;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;

public class PrintRequest extends Request {
  private final String message;

  public PrintRequest(Entity target,
                      Action origin, String message) {
    super(target, origin);

    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
