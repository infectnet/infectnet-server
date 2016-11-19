package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;

/**
 * Basic types for {@link Tile} objects.
 */
public enum TileType {
  /**
   * Any kind of {@link Entity} can be on this type of {@link Tile}.
   */
  CAVE,

  /**
   * No {@link Entity} can be on this type of {@link Tile}.
   */
  ROCK
}
