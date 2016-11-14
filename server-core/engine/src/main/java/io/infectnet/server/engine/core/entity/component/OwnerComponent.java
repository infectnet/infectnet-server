package io.infectnet.server.engine.core.entity.component;

import io.infectnet.server.engine.core.player.Player;

/**
 * Component holding the owner of an {@link io.infectnet.server.engine.core.entity.Entity}.
 */
public class OwnerComponent {
  private final Player owner;

  public OwnerComponent(Player owner) {
    this.owner = owner;
  }

  public Player getOwner() {
    return owner;
  }
}
