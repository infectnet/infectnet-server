package io.infectnet.server.engine.content.selector;


import static io.infectnet.server.engine.core.entity.Category.WORKER;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.selector.Selector;

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
