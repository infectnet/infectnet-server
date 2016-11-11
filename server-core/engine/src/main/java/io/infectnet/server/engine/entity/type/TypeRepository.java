package io.infectnet.server.engine.entity.type;

import io.infectnet.server.engine.entity.component.TypeComponent;

import java.util.Optional;
import java.util.Set;

/**
 * Interface for classes that can store available type instances. The idea behind
 * {@code TypeRepository} is to collect all available types in a single storage instead of
 * letting them float around everywhere in the code.
 */
public interface TypeRepository {
  /**
   * Registers the specified {@code TypeComponent} in the repository.
   * @param typeComponent the type to be registered
   * @param <T> the actual type of the {@code TypeComponent}
   * @throws NameAlreadyRegisteredException if a {@code TypeComponent} with the same name is
   * already registered
   */
  <T extends TypeComponent> void registerType(T typeComponent) throws NameAlreadyRegisteredException;

  /**
   * Removes the specified {@code TypeComponent}.
   * @param typeComponent the type to be removed
   * @param <T> the actual type of the {@code TypeComponent}
   */
  <T extends TypeComponent> void removeType(T typeComponent);

  /**
   * Gets the {@code TypeComponent} that has the specified name.
   * @param name the name
   * @return an {@code Optional} with the {@code TypeComponent} having the specified name or
   * an empty {@code Optional} if there were no such {@code TypeComponent} found
   */
  Optional<TypeComponent> getTypeByName(String name);

  /**
   * Gets the set of all registered {@code TypeComponent}s.
   * @return the set of registered types
   */
  Set<TypeComponent> getRegisteredTypes();
}
