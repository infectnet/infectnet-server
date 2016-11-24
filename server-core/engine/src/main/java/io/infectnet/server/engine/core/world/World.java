package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;

import java.util.List;

/**
 * The representation of the game world, in a finite two-dimensional space,
 * bounded by pre-set limitations of its maximum coordinates. The {@code World} regenerates
 * itself when it is first instantiated.
 */
public interface World {

  /**
   * Generates a new array of Tiles with the given strategy.
   * @param height the height of the world
   * @param width the width of the world
   */
  void generate(int height, int width);

  /**
   * Returns a list containing all Entities that are visible for the {@link Entity} given.
   * @param entity the {@code Entity} in the centre, whose point of view prevails
   * @return a list of the found Entities, an empty list if nothing was found
   */
  List<Entity> seenBy(Entity entity);

  /**
   * Returns the list of entities with zero distance from the given entity.
   * Scans the entity's neighbouring tiles for other entities.
   * @param entity the {@code Entity} in the centre, whose point of view prevails
   * @return a list of the found Entities, an empty list if nothing was found
   */
  List<Entity> neighboursOf(Entity entity);

  /**
   * Returns the list of all tiles seen by the passed entity.
   * @param entity the {@code Entity} in the centre, whose point of view prevails
   * @return a list of {@link Tile}s that can be seen from the given entity
   */
  List<Tile> viewSight(Entity entity);
}
