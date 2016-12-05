package io.infectnet.server.engine.content.status;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.status.StatusConsumer;
import io.infectnet.server.engine.core.status.StatusMessage;
import io.infectnet.server.engine.core.status.StatusPublisher;
import io.infectnet.server.engine.core.world.Tile;
import io.infectnet.server.engine.core.world.World;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class SynchronousStatusPublisher implements StatusPublisher {

  private final PlayerService playerService;

  private final EntityManager entityManager;

  private final World world;

  public SynchronousStatusPublisher(PlayerService playerService, EntityManager entityManager,
                                    World world) {
    this.playerService = playerService;

    this.entityManager = entityManager;

    this.world = world;
  }

  @Override
  public void publish(StatusConsumer statusConsumer) {

    for (Player p : playerService.getObservedPlayerList()) {
      statusConsumer.accept(p, new StatusMessage(getVisibleTilesForPlayer(p)));
    }

  }

  private Set<Tile> getVisibleTilesForPlayer(Player player) {
    Collection<Entity> playerEntities =
        entityManager.query().ofPlayer(player).inCategory(Category.BUILDING).execute();

    Set<Tile> visibleTiles = new HashSet<>();

    for (Entity e : playerEntities) {
      visibleTiles.addAll(world.viewSight(e));
    }

    return visibleTiles;
  }

}
