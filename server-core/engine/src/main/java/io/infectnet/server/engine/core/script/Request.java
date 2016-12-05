package io.infectnet.server.engine.core.script;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;

import java.util.Optional;

/**
 * A request that can be processed. {@code Request} represents a change request to the state of the
 * game (the World, the Entity System, etc.). {@code Request}s can have an origin {@link Action}
 * they were created from.
 */
public abstract class Request {
  private final Entity target;

  private final Action origin;

  /**
   * Constructs a new instance with the specified target {@code Entity} and origin {@code Action}.
   * The target of the request is the {@code Entity} to be modified. This parameter can be
   * {@code null}. The origin is the {@code Action} this {@code Request} was produced from. Origin
   * can be {@code null} too.
   * @param target the target {@code Entity} of the {@code Request}
   * @param origin the origin {@code Action}
   */
  public Request(Entity target, Action origin) {
    this.target = target;

    this.origin = origin;
  }

  public Optional<Entity> getTarget() {
    return Optional.ofNullable(target);
  }

  public Optional<Action> getOrigin() {
    return Optional.ofNullable(origin);
  }
}
