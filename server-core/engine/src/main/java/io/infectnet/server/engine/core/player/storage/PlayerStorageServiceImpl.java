package io.infectnet.server.engine.core.player.storage;

import io.infectnet.server.engine.core.player.Player;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStorageServiceImpl implements PlayerStorageService {

  private static final int MAX_STORAGE_SIZE = 10;

  private final Map<Player, PlayerStorage> playerStorageMap;

  public PlayerStorageServiceImpl() {
    playerStorageMap = new ConcurrentHashMap<>();
  }

  @Override
  public void addStorageForPlayer(Player player) {
    playerStorageMap.put(player, new PlayerStorage(MAX_STORAGE_SIZE, player));
  }

  @Override
  public void removeStorageForPlayer(Player player) {
    playerStorageMap.remove(player);
  }

  @Override
  public Optional<PlayerStorage> getStorageForPlayer(Player player) {
    return Optional.ofNullable(playerStorageMap.get(player));
  }

}
