package io.infectnet.server.engine.content.selector;

import static io.infectnet.server.engine.core.entity.Category.BUILDING;
import static io.infectnet.server.engine.core.entity.Category.FIGHTER;
import static io.infectnet.server.engine.core.entity.Category.WORKER;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.script.selector.Selector;
import io.infectnet.server.engine.core.world.World;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EnemySelector extends Selector {
  private final EntityManager entityManager;

  private final EntityWrapperRepository wrapperRepository;

  private final PlayerService playerService;

  private final World world;

  public EnemySelector(Player player, EntityManager entityManager,
                       EntityWrapperRepository wrapperRepository,
                       PlayerService playerService, World world) {
    super(player);
    this.entityManager = entityManager;
    this.wrapperRepository = wrapperRepository;
    this.playerService = playerService;
    this.world = world;
  }

  public List<EntityWrapper> getWorkers() {
    return getWrappersOfCategory(WORKER);
  }

  public List<EntityWrapper> getWorker() {
    return getWrappersOfCategory(WORKER);
  }

  public List<EntityWrapper> getFighters() {
    return getWrappersOfCategory(FIGHTER);
  }

  public List<EntityWrapper> getFighter() {
    return getWrappersOfCategory(FIGHTER);
  }

  public List<EntityWrapper> getBuildings() {
    return getWrappersOfCategory(BUILDING);
  }

  public List<EntityWrapper> getBuilding() {
    return getWrappersOfCategory(BUILDING);
  }

  private List<EntityWrapper> getWrappersOfCategory(Category category) {
    Collection<Entity> enemyEntities = getAllEnemyEntities(category);

    Set<Entity> seenEntities = getEntitiesSeenByPlayer();

    List<EntityWrapper> wrappers = new LinkedList<>();

    for (Entity entity : enemyEntities) {
      if (seenEntities.contains(entity)) {
        wrappers.add(wrapperRepository.wrapEntity(entity));
      }
    }

    return wrappers;
  }

  private Set<Entity> getEntitiesSeenByPlayer() {
    Collection<Entity> buildings = entityManager.query()
        .ofPlayer(player)
        .inCategory(BUILDING)
        .execute();

    Set<Entity> seenEntities = new HashSet<>();

    for (Entity building : buildings) {
      seenEntities.addAll(world.seenBy(building));
    }
    return seenEntities;
  }

  private Collection<Entity> getAllEnemyEntities(Category category) {
    Collection<Entity> enemyEntities = new LinkedList<>();

    for (Player p : playerService.getAllPlayers()) {
      if (!p.equals(player)) {
        Collection<Entity> entities = entityManager.query()
            .ofPlayer(player)
            .inCategory(category)
            .execute();

        enemyEntities.addAll(entities);
      }
    }

    return enemyEntities;
  }
}
