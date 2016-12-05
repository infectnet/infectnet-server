package io.infectnet.server.controller.engine;

import io.infectnet.server.common.configuration.ConfigurationHolder;
import io.infectnet.server.engine.Engine;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.generation.CompilationError;
import io.infectnet.server.engine.core.status.StatusConsumer;
import io.infectnet.server.service.user.UserDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Default implementation of {@link EngineConnector}.
 */
public class EngineConnectorImpl implements EngineConnector {

  private static final String DESIRED_TICK_DURATION_PROPERTY = "desired_tick_duration";

  private final Engine engine;

  public EngineConnectorImpl(StatusConsumer statusConsumer) {
    this.engine = Engine.create(statusConsumer);
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
  public CompletableFuture<List<CompilationError>> compileAndUploadForUser(UserDTO user,
                                                                           String source) {
    Player player = createOrGetPlayer(user);

    CompletableFuture<List<CompilationError>> result = new CompletableFuture<>();

    CompletableFuture.runAsync(() -> {
      try {
        result.complete(engine.compileAndUploadForPlayer(player, source));
      } catch (Exception e) {
        result.completeExceptionally(e);
      }
    });

    return result;
  }

  @Override
  public String getSourceCodeForUser(UserDTO user) {
    Player player = createOrGetPlayer(user);
    Optional<String> source = engine.getSourceCodeForPlayer(player);

    return source.orElse(StringUtils.EMPTY);
  }

  @Override
  public void setUserAsObserved(UserDTO user) {
    engine.setPlayerAsObserved(createOrGetPlayer(user));
  }

  @Override
  public void removeUserFromObserved(UserDTO user) {
    engine.removePlayerFromObserved(createOrGetPlayer(user));
  }

  private Player createOrGetPlayer(UserDTO user) {
    return engine.createOrGetPlayer(user.getUserName());
  }
}
