package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;

/**
 * This class represents an atomic piece of the {@link World}.
 * It holds information about the type of area defined by this {@code Tile}'s unique coordinates.
 */
public class Tile {

  private TileType type;

  private Entity entity;

  private Position position;

  public Tile(TileType type, Position position) {
    this.type = type;
    this.position = position;
  }

  public boolean isBlockedOrOccupied(){
    return type == TileType.ROCK || entity != null;
  }

  public TileType getType() {
    return type;
  }

  public Entity getEntity() {
    return entity;
  }

  public Position getPosition(){
    return position;
  }

  public void setEntity(Entity entity) {
    this.entity = entity;
  }
}
