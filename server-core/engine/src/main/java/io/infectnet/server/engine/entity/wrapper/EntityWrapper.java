package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;

/**
 * Wrapper (or proxy class) for {@link Entity} in order to make it safe to expose them to
 * Groovy DSL code.
 * <p>
 *   Descendants of this class <b>must</b> be written in Groovy so they can implement traits and
 *   have meta class.
 * </p>
 */
public abstract class EntityWrapper {
  /**
   * The wrapped {@code Entity} object. Note the absence of getter method. It's avoided
   * to prevent Groovy DSL code from accessing the wrapped {@code Entity}.
   */
  protected final Entity wrappedEntity;

  /**
   * Constructs a new instance wrapping the specified {@code Entity}.
   * @param wrappedEntity the {@code Entity} to be wrapped
   */
  public EntityWrapper(Entity wrappedEntity) {
    this.wrappedEntity = wrappedEntity;
  }
}
