package io.infectnet.server.engine.core.entity.wrapper;


import io.infectnet.server.engine.core.entity.Entity;

/**
 * Class that represents an action coming from the player code. Methods called on wrappers can
 * produce {@code Action}s that can be propagated to the engine which in turn executes them.
 */
public abstract class Action {
  private final Entity source;

  /**
   * Constructs an {@code Action} initiated by the specified {@code Entity}
   * @param source the {@code Entity} that initiated the {@code Action}
   */
  public Action(Entity source) {
    this.source = source;
  }

  public Entity getSource() {
    return source;
  }
}
