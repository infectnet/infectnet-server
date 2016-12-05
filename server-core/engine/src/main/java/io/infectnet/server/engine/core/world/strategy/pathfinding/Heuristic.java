package io.infectnet.server.engine.core.world.strategy.pathfinding;

import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

/**
 * Interface to represent heuristic function, which calculate a value used in path-finding.
 */
public interface Heuristic {

  /**
   * Returns a value calculated with the given arguments.
   * @param world the current map of the game
   * @param currentPosition one of the given position
   * @param targetPosition one of the given positions
   * @return a value calculated
   */
  int heuristic(World world, Position currentPosition, Position targetPosition);
}
