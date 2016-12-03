package io.infectnet.server.engine.core.entity.wrapper;

import io.infectnet.server.engine.core.entity.Entity;

import java.util.function.BiConsumer;

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
   * A {@code Consumer} that accepts created actions and propagates them to the engine
   * along with the current mutable state of the wrapper
   */
  protected final BiConsumer<WrapperState, Action> actionConsumer;

  protected final WrapperState state;

  /**
   * Constructs a new instance wrapping the specified {@code Entity}.
   * @param wrappedEntity the {@code Entity} to be wrapped
   * @param actionConsumer a consumer that accepts created actions and propagates them to the engine
   * along with the current mutable state of the wrapper
   */
  public EntityWrapper(Entity wrappedEntity, BiConsumer<WrapperState, Action> actionConsumer) {
    this.wrappedEntity = wrappedEntity;

    this.actionConsumer = actionConsumer;

    this.state = this.new WrapperState();
  }

  /**
   * Returns the wrapped {@link Entity}'s type name.
   * @return the type name
   */
  public String getType() {
    return wrappedEntity.getTypeComponent().getName();
  }

  public class WrapperState {
    private boolean interactedWith;

    public boolean isInteractedWith() {
      return interactedWith;
    }

    public void setInteractedWith(boolean interactedWith) {
      this.interactedWith = interactedWith;
    }
  }
}
