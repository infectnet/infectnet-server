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
   * Generates a new array of Tiles with a Cellular Automaton.
   */
  void generate(int height, int width);

  /**
   * Returns a list containing all Entities that are visible for the {@link Entity} given.
   * @param entity the {@code Entity} in the centre, whose point of view prevails.
   * @return a list of the found Entities.
   */
  List<Entity> listOfEntitiesVisible(Entity entity);
}
