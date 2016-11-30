package io.infectnet.server.engine.core.world.strategy.pathfinding;

import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

/**
 * A heuristic function for the path finding algorithm, with bounded relaxation.
 * Calculating the estimated distance between two positions and scaling it up with the epsilon parameter.
 * The main purpose is to trade finding the optimal path to finding a good-enough path faster.
 */
public class WeightedHeuristic implements Heuristic{

    /**
     * The parameter of the weighing.
     */
    private final int epsilon = 5;

    /**
     * The chosen admissible heuristic function, that this heuristic uses to calculate its value.
     */
    private final Heuristic heuristic = new ClosestHeuristic();

    @Override
    public int heuristic(World world, Position currentPosition, Position targetPosition) {
        return epsilon * heuristic.heuristic(world, currentPosition,targetPosition);
    }
}
