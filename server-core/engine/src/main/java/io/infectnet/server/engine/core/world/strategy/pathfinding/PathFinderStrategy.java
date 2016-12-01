package io.infectnet.server.engine.core.world.strategy.pathfinding;

import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.Tile;
import io.infectnet.server.engine.core.world.World;

import java.util.List;

/**
 * Interface for Path finding in the world between two Positions.
 */
public interface PathFinderStrategy {

  /**
   * Finds a path from given start Position to the target Position,
   * made of adjacent Tiles linked together in a list
   * @param world the map where we want to search for the path
   * @param start the starting Position
   * @param target the destination Position
   * @return a linked list containing the found path
   */
  List<Tile> findPath(World world, Position start, Position target);
}
