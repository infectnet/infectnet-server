package io.infectnet.server.engine.core.world.strategy.pathfinding;

import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

/**
 * A simple heuristic function for the path finding algorithm.
 * Calculating the estimated distance between two positions.
 */
public class ClosestHeuristic implements Heuristic {

  @Override
  public int heuristic(World world, Position currentPosition, Position targetPosition) {
    return Math.abs(currentPosition.getH() - targetPosition.getH()) + Math
        .abs(currentPosition.getW() - targetPosition.getW());
  }
}
