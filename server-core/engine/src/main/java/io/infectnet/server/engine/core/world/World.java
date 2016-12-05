package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.world.strategy.generation.WorldGeneratorStrategy;
import io.infectnet.server.engine.core.world.strategy.pathfinding.PathFinderStrategy;

import java.util.List;
import java.util.Set;

/**
 * The representation of the game world, in a finite two-dimensional space,
 * bounded by pre-set limitations of its maximum coordinates. The {@code World} regenerates
 * itself when it is first instantiated.
 */
public abstract class World {
  protected final WorldGeneratorStrategy worldGeneratorStrategy;

  protected final PathFinderStrategy pathFinderStrategy;

  public World(WorldGeneratorStrategy worldGeneratorStrategy,
               PathFinderStrategy pathFinderStrategy) {
    this.worldGeneratorStrategy = worldGeneratorStrategy;
    this.pathFinderStrategy = pathFinderStrategy;
  }

  /**
   * Generates a new array of Tiles with the given strategy.
   * @param height the height of the world
   * @param width the width of the world
   */
  public abstract void generate(int height, int width);

  public List<Tile> findPath(Position start, Position target) {
    return pathFinderStrategy.findPath(this, start, target);
  }

  /**
   * Returns a set containing all Entities that are visible for the {@link Entity} given.
   * @param entity the {@code Entity} in the centre, whose point of view prevails
   * @return a set of the found Entities, an empty set if nothing was found
   */
  public abstract Set<Entity> seenBy(Entity entity);

  /**
   * Returns the list of entities with zero distance from the given entity.
   * Scans the entity's neighbouring tiles for other entities.
   * @param entity the {@code Entity} in the centre, whose point of view prevails
   * @return a list of the found Entities, an empty list if nothing was found
   */
  public abstract Set<Entity> neighboursOf(Entity entity);

  /**
   * Returns the list of all tiles seen by the passed entity.
   * @param entity the {@code Entity} in the centre, whose point of view prevails
   * @return a list of {@link Tile}s that can be seen from the given entity
   */
  public abstract List<Tile> viewSight(Entity entity);

  /**
   * Returns a Tile defined by the given Position.
   * @param position the position given
   * @return the tile
   */
  public abstract Tile getTileByPosition(Position position);

  /**
   * Checks if the Position given defines a coordinate pair in the field of the World.
   * @param position the given coordinates to check
   * @return true if it is between the borders and false otherwise
   */
  public abstract boolean isPositionValidTile(Position position);

  /**
   * Sets the entity on the Tile defined by the given Position
   * @param entity the entity to be set
   * @param position the position given
   */
  public abstract void setEntityOnPosition(Entity entity, Position position);

  public abstract int getHeight();

  public abstract int getWidth();
}
