package io.infectnet.server.engine.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

public class PlayerServiceImpl implements PlayerService {
  private final Map<String, Player> playerMap;

  private final Set<Player> observedPlayers;

  private final Function<Player, Player> playerInitializer;

  /**
   * Constructs a new instance with the specified initializer function. This function will be used
   * to set the various fields of a newly created {@link Player} instance to appropriate starter
   * values.
   * @param playerInitializer the function that will be used to initialize new {@code Player}
   * instances
   */
  public PlayerServiceImpl(Function<Player, Player> playerInitializer) {
    this.playerMap = new ConcurrentHashMap<>();

    this.observedPlayers = new CopyOnWriteArraySet<>();

    this.playerInitializer = playerInitializer;
  }

  @Override
  public List<Player> getAllPlayers() {
    return new ArrayList<>(playerMap.values());
  }

  @Override
  public Optional<Player> createPlayer(String username) {
    // avoid throwing NPE in favor of Optional
    if (playerMap.containsKey(username) || username == null) {
      return Optional.empty();
    }

    try {
      Player initializedPlayer = playerInitializer.apply(new Player(username));

      playerMap.put(username, initializedPlayer);

      return Optional.of(initializedPlayer);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Player> getPlayerByUsername(String username) {
    return Optional.ofNullable(playerMap.get(username));
  }

  @Override
  public boolean isPlayerObserved(Player player) {
    return observedPlayers.contains(player);
  }

  @Override
  public List<Player> getObservedPlayerList() {
    return new ArrayList<>(observedPlayers);
  }

  @Override
  public void setPlayerAsObserved(Player player) {
    observedPlayers.add(player);
  }

  @Override
  public void removePlayerFromObserved(Player player) {
    observedPlayers.remove(player);
  }
}
