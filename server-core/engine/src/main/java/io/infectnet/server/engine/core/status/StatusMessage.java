package io.infectnet.server.engine.core.status;

import io.infectnet.server.engine.core.world.Tile;

import java.util.Set;

public class StatusMessage {

  private final Set<Tile> tileSet;

  public StatusMessage(Set<Tile> tileSet) {
    this.tileSet = tileSet;
  }

  public Set<Tile> getTileSet() {
    return tileSet;
  }
}
