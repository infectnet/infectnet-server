package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;

/**
 * Interface for factories that can construct {@link EntityWrapper} instances for {@link Entity}
 * objects.
 * @param <T> the type of the created wrapper object
 */
public interface EntityWrapperFactory<T extends EntityWrapper> {
  /**
   * Creates a wrapper or proxy for the specified {@code Entity} instance. The created wrapper can
   * be safely passed to the Groovy DSL.
   * @param entity the {@code Entity} to be wrapped
   * @return an appropriate wrapper for the specified {@code Entity}
   */
  T wrapEntity(Entity entity);
}
