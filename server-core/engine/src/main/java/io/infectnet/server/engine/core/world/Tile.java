package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;

/**
 * This class represents an atomic piece of the {@link World}.
 * It holds information about the type of area defined by this {@code Tile}'s unique coordinates.
 */
public class Tile {

  private TileType type;

  private Entity entity;

  public Tile(TileType type) {
    this.type = type;
  }

  public TileType getType() {
    return type;
  }

  public Entity getEntity() {
    return entity;
  }

  public void setEntity(Entity entity) {
    this.entity = entity;
  }
}
