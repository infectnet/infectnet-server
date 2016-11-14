package io.infectnet.server.engine.content.print;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;

public class PrintAction extends Action {
  private final String message;

  public PrintAction(Entity source, String message) {
    super(source);

    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
