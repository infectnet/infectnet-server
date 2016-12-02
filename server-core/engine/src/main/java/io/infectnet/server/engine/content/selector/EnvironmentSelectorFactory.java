package io.infectnet.server.engine.content.selector;

import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.script.selector.SelectorFactory;
import io.infectnet.server.engine.core.world.World;

public class EnvironmentSelectorFactory extends SelectorFactory<EnvironmentSelector> {
  private static final String NAME = "env";

  private final EntityManager entityManager;

  private final EntityWrapperRepository wrapperRepository;

  private final PlayerService playerService;

  private final World world;

  public EnvironmentSelectorFactory(EntityManager entityManager,
                                    EntityWrapperRepository wrapperRepository,
                                    PlayerService playerService,
                                    World world) {
    super(NAME);
    this.entityManager = entityManager;
    this.wrapperRepository = wrapperRepository;
    this.playerService = playerService;
    this.world = world;
  }

  @Override
  public EnvironmentSelector forPlayer(Player player) {
    return new EnvironmentSelector(player, entityManager, wrapperRepository, playerService, world);
  }
}
