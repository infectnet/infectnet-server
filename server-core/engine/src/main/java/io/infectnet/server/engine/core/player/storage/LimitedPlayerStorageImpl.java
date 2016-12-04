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

  private final Map<String, Object> attributeMap;

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
    attributeMap = new HashMap<>();
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
  public Map<String, Object> getAttributeMap() {
    return Collections.unmodifiableMap(attributeMap);
  }

  @Override
  public Optional<Object> getAttribute(String attributeName) {
    return Optional.ofNullable(attributeMap.get(attributeName));
  }

  @Override
  public void setAttribute(String attributeName, Object value) {
    attributeMap.put(attributeName, value);
  }

  @Override
  public void removeAttribute(String attributeName) {
    attributeMap.remove(attributeName);
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
