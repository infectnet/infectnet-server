package io.infectnet.server.engine.content.selector;

import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.selector.SelectorFactory;

public class OwnSelectorFactory extends SelectorFactory<OwnSelector> {
  private static final String SELECTOR_NAME = "own";

  private final EntityManager entityManager;

  private final EntityWrapperRepository wrapperRepository;

  public OwnSelectorFactory(EntityManager entityManager,
                            EntityWrapperRepository wrapperRepository) {
    super(SELECTOR_NAME);

    this.entityManager = entityManager;

    this.wrapperRepository = wrapperRepository;
  }

  @Override
  public OwnSelector forPlayer(Player player) {
    return new OwnSelector(player, entityManager, wrapperRepository);
  }
}