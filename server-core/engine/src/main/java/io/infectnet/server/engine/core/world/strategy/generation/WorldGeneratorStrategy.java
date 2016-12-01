package io.infectnet.server.engine.core.world.strategy.generation;

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
   * Generates a much simplified form of a {@link io.infectnet.server.engine.core.world.World},
   * using booleans instead of {@link io.infectnet.server.engine.core.world.TileType}s.
   * @param height the height of the generated world
   * @param width the width of the generated world
   * @return a boolean array containing the data about all tiles that were generated.
   */
  boolean[][] generateWorld(int height, int width);
}
