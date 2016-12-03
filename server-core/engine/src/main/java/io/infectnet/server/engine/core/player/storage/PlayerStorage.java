package io.infectnet.server.engine.core.player.storage;

import io.infectnet.server.engine.core.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Storage class used for saving data for a {@link Player}.
 */
public class PlayerStorage {

  private final int maxSize;

  private final Player owner;

  private final Map<String, Object> storageMap;

  /**
   * Constructs a new storage.
   * @param maxSize the max number of saved records
   * @param owner the owner of the storage
   */
  public PlayerStorage(int maxSize, Player owner) {
    this.maxSize = maxSize;
    this.owner = owner;

    storageMap = new HashMap<>();
  }

  /**
   * Queries the storage for the requested record.
   * @param resourceName the name of the requested record
   * @return an {@code Optional} of the record
   */
  public Optional<Object> getRecord(String resourceName) {
    return Optional.ofNullable(storageMap.get(resourceName));
  }

  /**
   * Assigns a value to a given key in the storage.
   * @param recordName the key
   * @param value the value to store
   */
  public void setRecord(String recordName, Object value) {
    if (storageMap.containsKey(recordName) || storageMap.size() + 1 <= maxSize) {
      storageMap.put(recordName, value);
    }
  }

  /**
   * Removes a record from the storage.
   * @param recordName the name of the record
   */
  public void removeRecord(String recordName) {
    storageMap.remove(recordName);
  }

  public Player getOwner() {
    return owner;
  }
}
