package io.infectnet.server.engine.core.player.storage;

import io.infectnet.server.engine.core.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerStorageImpl implements PlayerStorage {

  private final int maxSize;

  private final Player owner;

  private final Map<String, Object> resourceMap;

  private final Map<String, Object> recordMap;

  /**
   * Constructs a new storage for the given player.
   * @param maxSize the max number of saved records
   * @param owner the owner of the storage
   */
  public PlayerStorageImpl(int maxSize, Player owner) {
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
}
