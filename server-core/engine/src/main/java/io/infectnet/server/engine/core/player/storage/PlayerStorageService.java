package io.infectnet.server.engine.core.player.storage;


import io.infectnet.server.engine.core.player.Player;

import java.util.Optional;

/**
 * Interface for accessing {@link Player}-level storage objects.
 */
public interface PlayerStorageService {

  /**
   * Creates a new storage for the given player.
   * @param player the owner of the new storage
   */
  void addStorageForPlayer(Player player);

  /**
   * Deletes the storage of the given player.
   * @param player the owner of the storage to be deleted
   */
  void removeStorageForPlayer(Player player);

  /**
   * Gets the storage of a player.
   * @param player the owner of the storage
   * @return an {@code Optional} of the storage
   */
  Optional<PlayerStorage> getStorageForPlayer(Player player);

}
