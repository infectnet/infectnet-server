package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;
import io.infectnet.server.engine.entity.component.TypeComponent;

/**
 * {@code EntityWrapperRepository} stores factories for different {@link TypeComponent}s.
 * {@link Entity} instances with the same {@code TypeComponent} should be wrapped into
 * {@link EntityWrapper} instances with the same <b>runtime</b> capabilities. By querying
 * factories from an instance of this class, this rule can be ensured, since the same factory
 * will always produce the same wrapper.
 */
public interface EntityWrapperRepository {
  /**
   * Registers a factory capable of creating {@code EntityWrapper} objects with the specified
   * type.
   * @param typeComponent the type to be registered
   * @param wrapperFactory the factory that will create wrappers with the appropriate type
   * @param <T> the static type of the created wrapper object
   */
  <T extends EntityWrapper> void registerFactoryForType(TypeComponent typeComponent,
                                                        EntityWrapperFactory<T> wrapperFactory);

  /**
   * Gets the {@code EntityWrapperFactory} capable of creating wrappers for the specified
   * type if registered in this repository instance.
   * @param typeComponent the type to be looked up
   * @return a factory capable of creating entities with the specified type
   * @throws FactoryNotFoundException if there is no factory registered for the specified type
   */
  EntityWrapperFactory<? extends EntityWrapper> getFactoryForType(TypeComponent typeComponent);

  /**
   * Creates a wrapped instance of the specified {@code Entity}.
   * @param entity the {@code Entity} to be wrapped
   * @return an appropriate wrapper for the specified {@code Entity}
   * @throws FactoryNotFoundException if there is no factory registered for the {@link TypeComponent}
   * of the specified {@code Entity}
   */
  EntityWrapper wrapEntity(Entity entity);
}
