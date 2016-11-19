package io.infectnet.server.engine.core.world;

/**
 * This interface is responsible for the map generation,
 * and uses the Strategy Design Pattern.
 */
public interface MapGeneratorStrategy {
    /**
     * Generates a much simplified form of a {@link Map}, using booleans instead of {@link TileType}s.
     * @return a boolean array containing the data about all tiles that were generated.
     */
    boolean[][] generateMap();
}
