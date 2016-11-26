package io.infectnet.server.engine.core.world.strategy;

import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.Tile;

import java.util.LinkedList;

/**
 * Interface for Path finding in the world.
 */
public interface PathFinderStrategy {

    /**
     * Searches for a path made of neighbouring {@link Tile}s connecting start Position and the target Position.
     * @param start
     * @param target
     * @return
     */
    LinkedList<Tile> findPath(Position start, Position target);
}
