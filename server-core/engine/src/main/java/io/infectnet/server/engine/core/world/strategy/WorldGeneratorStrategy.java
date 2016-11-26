package io.infectnet.server.engine.core.world.strategy;

import io.infectnet.server.engine.core.world.TileType;
import io.infectnet.server.engine.core.world.World;

/**
 * This interface is responsible for the world generation,
 * and uses the Strategy Design Pattern.
 */
public interface WorldGeneratorStrategy {
    /**
     * The first type of cell, that the world consist of.
     */
    boolean CAVE = true;

    /**
     * The second type of cell, that the world consist of.
     */
    boolean ROCK = false;

    /**
     * Generates a much simplified form of a {@link World}, using booleans instead of {@link TileType}s.
     * @param height the height of the generated world
     * @param width the width of the generated world
     * @return a boolean array containing the data about all tiles that were generated.
     */
    boolean[][] generateWorld(int height, int width);
}
