package io.infectnet.server.engine;

import io.infectnet.server.engine.entity.Entity;

import java.util.Optional;

/**
 * A request that can be processed. Although identical to
 * {@link io.infectnet.server.engine.entity.wrapper.Action} this class has a completely different
 * usage, so they must not be used in place of each other. {@code Request} represents a change
 * request to the state of the game (the World, the Entity System, etc.).
 */
public abstract class Request {
  private final Entity target;

  /**
   * Constructs a new instance with no target.
   */
  public Request() {
    this.target = null;
  }

  /**
   * Constructs a new instance with the specified target {@code Entity}. The target of the request
   * is the {@code Entity} to be modified.
   * @param target the target {@code Entity} of the {@code Request}
   */
  public Request(Entity target) {
    this.target = target;
  }

  public Optional<Entity> getTarget() {
    return Optional.ofNullable(target);
  }
}
