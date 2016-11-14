package io.infectnet.server.engine.core.entity;

import io.infectnet.server.engine.core.player.Player;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * A queryable storage of {@link Entity} instances.
 */
public interface EntityManager {
  /**
   * Constructs a new {@code Query} object capable of querying {@link Entity} instance from this
   * manager.
   * @return a new {@code Query}
   */
  Query query();

  /**
   * Adds the specified {@code Entity} to this manager. Added entities are called the managed
   * entities of the manager.
   * @param entity the {@code Entity} to be added
   */
  void addEntity(Entity entity);

  /**
   * Removed the specified {@code Entity} from the list of managed entities. The manager will
   * remove all its references to the {@code Entity}.
   * @param entity the {@code Entity} to be removed
   */
  void removeEntity(Entity entity);

  /**
   * Fluent interface that enables clients to query {@link Entity} instances from an
   * {@code EntityManager} in a comfortable way.
   */
  interface Query {
    /**
     * Filters the entities returning the ones owned by the specified {@code Player}. This filter
     * cannot be omitted. Subsequent calls will replace the previously specified {@code Player}.
     * @param player the owner of the entities
     * @return a {@code Query} with this filter set
     */
    Query ofPlayer(Player player);

    /**
     * Filters the entities returning the ones with the specified {@code Category}. This filter
     * cannot be omitted. Subsequent calls will replace the previously specified {@code Category}.
     * @param category the filtering {@code Category}
     * @return a {@code Query} with this filter set
     */
    Query inCategory(Category category);

    /**
     * Filters the entities with the specified predicate. Multiple predicates can be specified.
     * Multiple predicates will be {@code AND}'d.
     * @param predicate the predicate to be satisfied by the entitie
     * @return a {@code Query} with this filter set
     */
    Query satisfying(Predicate<Entity> predicate);

    /**
     * Executes the query and returns the entities satisfying the requirements.
     * @return a collection of {@code Entity} objects
     */
    Collection<Entity> execute();
  }
}
