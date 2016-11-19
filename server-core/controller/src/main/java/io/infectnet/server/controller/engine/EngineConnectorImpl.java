package io.infectnet.server.controller.engine;

import io.infectnet.server.common.configuration.ConfigurationHolder;
import io.infectnet.server.engine.Engine;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.service.user.UserDTO;

import java.util.concurrent.CompletableFuture;

/**
 * Default implementation of {@link EngineConnector}.
 */
public class EngineConnectorImpl implements EngineConnector {

  private static final String DESIRED_TICK_DURATION_PROPERTY = "desired_tick_duration";

  private final Engine engine;


  public EngineConnectorImpl() {
    this.engine = Engine.create();
  }

  @Override
  public void start() {
    Long desiredTickDuration = Long.parseLong(
        ConfigurationHolder.INSTANCE.getActiveConfiguration().get(DESIRED_TICK_DURATION_PROPERTY));

    engine.start(desiredTickDuration);
  }

  @Override
  public boolean stop(StopType stopType) {
    switch (stopType) {
      case BLOCKING:
        return engine.stopBlocking();
      case ASYNC:
        return engine.stopAsync();
      default:
        return false;
    }
  }

  @Override
  public CompletableFuture<Void> compileAndUploadForUser(UserDTO user, String source) {
    Player player = createOrGetPlayer(user);

    CompletableFuture<Void> result = new CompletableFuture<>();

    CompletableFuture.runAsync(() -> {
      try {
        engine.compileAndUploadForPlayer(player, source);
        result.complete(null);
      } catch (Exception e) {
        result.completeExceptionally(e);
      }
    });

    return result;
  }

  private Player createOrGetPlayer(UserDTO user) {
    return engine.createOrGetPlayer(user.getUserName());
  }
}
