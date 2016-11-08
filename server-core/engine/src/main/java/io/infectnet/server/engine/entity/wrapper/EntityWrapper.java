package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;

public abstract class EntityWrapper {
  /**
   * The wrapped {@code Entity} object. Note the absence of getter method. It's avoided
   * to prevent Groovy DSL code from accessing the wrapped {@code Entity}.
   */
  protected final Entity wrappedEntity;

  public EntityWrapper(Entity wrappedEntity) {
    this.wrappedEntity = wrappedEntity;
  }
}
