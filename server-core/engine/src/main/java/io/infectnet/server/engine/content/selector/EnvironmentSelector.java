package io.infectnet.server.engine.content.selector;

import static io.infectnet.server.engine.core.entity.Category.BUILDING;
import static io.infectnet.server.engine.core.entity.Category.RESOURCE;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.selector.Selector;
import io.infectnet.server.engine.core.world.World;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EnvironmentSelector extends Selector {
  private final EntityManager entityManager;

  private final EntityWrapperRepository wrapperRepository;

  private final Player environmentPlayer;

  private final World world;

  public EnvironmentSelector(Player player, EntityManager entityManager,
                             EntityWrapperRepository wrapperRepository,
                             Player environmentPlayer,
                             World world) {
    super(player);

    this.entityManager = entityManager;
    this.wrapperRepository = wrapperRepository;
    this.environmentPlayer = environmentPlayer;
    this.world = world;
  }

  public List<EntityWrapper> getResources() {
    return getWrappersOfCategory(RESOURCE);
  }

  public List<EntityWrapper> getResource() {
    return getWrappersOfCategory(RESOURCE);
  }

  private List<EntityWrapper> getWrappersOfCategory(Category category) {
    Collection<Entity> entities = getAllEnvironmentEntities(category);

    Set<Entity> seenEntities = getEntitiesSeenByPlayer();

    List<EntityWrapper> wrappers = new LinkedList<>();

    for (Entity entity : entities) {
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

  private Collection<Entity> getAllEnvironmentEntities(Category category) {
    return entityManager.query()
          .ofPlayer(environmentPlayer)
          .inCategory(category)
          .execute();
  }
}
