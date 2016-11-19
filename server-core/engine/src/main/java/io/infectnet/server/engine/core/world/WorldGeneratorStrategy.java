package io.infectnet.server.engine.core.world;

/**
 * This interface is responsible for the map generation,
 * and uses the Strategy Design Pattern.
 */
public interface WorldGeneratorStrategy {
    /**
     * The first type of cell, that the map consist of.
     */
    boolean CAVE = true;

    /**
     * The second type of cell, that the map consist of.
     */
    boolean ROCK = false;

    /**
     * Generates a much simplified form of a {@link World}, using booleans instead of {@link TileType}s.
     * @param height the height of the generated map
     * @param width the width of the generated map
     * @return a boolean array containing the data about all tiles that were generated.
     */
    boolean[][] generateMap(int height, int width);
}
