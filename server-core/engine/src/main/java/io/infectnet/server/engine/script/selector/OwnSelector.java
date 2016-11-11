package io.infectnet.server.engine.script.selector;

import static io.infectnet.server.engine.entity.Category.WORKER;

import io.infectnet.server.engine.entity.Entity;
import io.infectnet.server.engine.entity.EntityManager;
import io.infectnet.server.engine.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.player.Player;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class OwnSelector extends Selector {
  private final EntityManager entityManager;

  private final EntityWrapperRepository wrapperRepository;

  public OwnSelector(Player player, EntityManager entityManager,
                     EntityWrapperRepository wrapperRepository) {
    super(player);

    this.entityManager = entityManager;

    this.wrapperRepository = wrapperRepository;
  }

  public List<EntityWrapper> getWorkers() {
    Collection<Entity> results =
        entityManager.query()
          .ofPlayer(this.player)
          .inCategory(WORKER)
          .execute();

    List<EntityWrapper> wrappers = new LinkedList<>();

    for (Entity entity : results) {
      wrappers.add(wrapperRepository.wrapEntity(entity));
    }

    return wrappers;
  }

  public List<EntityWrapper> getWorker() {
    return getWorkers();
  }
}
