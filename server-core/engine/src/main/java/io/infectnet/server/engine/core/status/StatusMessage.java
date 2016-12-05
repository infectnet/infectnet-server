package io.infectnet.server.engine.core.status;

import io.infectnet.server.engine.core.world.Tile;

import java.util.Set;

/**
 * Status update message, used for sending map updates.
 */
public class StatusMessage {

  private final Set<Tile> tileSet;

  /**
   * Constructs a new status update message containing the given map tiles.
   * @param tileSet the map tiles to be sent
   */
  public StatusMessage(Set<Tile> tileSet) {
    this.tileSet = tileSet;
  }

  /**
   * Gets the updated map tiles.
   * @return the map tiles
   */
  public Set<Tile> getTileSet() {
    return tileSet;
  }
}
