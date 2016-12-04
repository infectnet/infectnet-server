package io.infectnet.server.engine.core.player.storage;

import io.infectnet.server.engine.core.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of {@code PlayerStorage} limiting the number of records saved by the player.
 */
public class LimitedPlayerStorageImpl implements PlayerStorage {

  private final int maxSize;

  private final Player owner;

  private final Map<String, Object> resourceMap;

  private final Map<String, Object> recordMap;

  /**
   * Constructs a new storage for the given player with the given max size.
   * @param maxSize the max number of saved records
   * @param owner the owner of the storage
   */
  public LimitedPlayerStorageImpl(int maxSize, Player owner) {
    this.maxSize = maxSize;
    this.owner = owner;

    recordMap = new HashMap<>();
    resourceMap = new HashMap<>();
  }

  @Override
  public Map<String, Object> getRecordMap() {
    return Collections.unmodifiableMap(recordMap);
  }

  @Override
  public Optional<Object> getRecord(String recordName) {
    return Optional.ofNullable(recordMap.get(recordName));
  }

  @Override
  public void setRecord(String recordName, Object value) {
    if (recordMap.containsKey(recordName) || recordMap.size() + 1 <= maxSize) {
      recordMap.put(recordName, value);
    }
  }

  @Override
  public void removeRecord(String recordName) {
    recordMap.remove(recordName);
  }

  @Override
  public Map<String, Object> getResourceMap() {
    return Collections.unmodifiableMap(resourceMap);
  }

  @Override
  public Optional<Object> getResource(String resourceName) {
    return Optional.ofNullable(resourceMap.get(resourceName));
  }

  @Override
  public void setResource(String resourceName, Object value) {
    resourceMap.put(resourceName, value);
  }

  @Override
  public void removeResource(String resourceName) {
    resourceMap.remove(resourceName);
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  /**
   * Returns the max number of saved records.
   * @return the max save records
   */
  public int getMaxSize() {
    return maxSize;
  }
}
