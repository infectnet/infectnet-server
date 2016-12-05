package io.infectnet.server.engine.content.status;

import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.status.StatusConsumer;
import io.infectnet.server.engine.core.status.StatusPublisher;


public class SynchronousStatusPublisher implements StatusPublisher {

  private final PlayerService playerService;

  public SynchronousStatusPublisher(PlayerService playerService) {
    this.playerService = playerService;
  }

  @Override
  public void publish(StatusConsumer statusConsumer) {

    for (Player p : playerService.getAllPlayers()) {

    }

  }

}
