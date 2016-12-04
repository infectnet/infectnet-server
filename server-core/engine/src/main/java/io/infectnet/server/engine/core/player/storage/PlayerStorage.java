package io.infectnet.server.engine.core.player.storage;

import io.infectnet.server.engine.core.player.Player;

import java.util.Map;
import java.util.Optional;

/**
 * Interface for storing player and system managed data.
 */
public interface PlayerStorage {

  /**
   * Returns an unmodifiable view of the storage managed by the player.
   * @return the player storage map
   */
  Map<String, Object> getRecordMap();

  /**
   * Queries the player storage for the requested record.
   * @param recordName the name of the requested record
   * @return an {@code Optional} of the record
   */
  Optional<Object> getRecord(String recordName);

  /**
   * Assigns a value to a given key in the player storage.
   * @param recordName the key
   * @param value the value to store
   */
  void setRecord(String recordName, Object value);

  /**
   * Removes a record from the player storage.
   * @param recordName the name of the record
   */
  void removeRecord(String recordName);

  /**
   * Returns an unmodifiable view of the storage managed by the system.
   * @return the system storage map
   */
  Map<String, Object> getAttributeMap();

  /**
   * Queries the system storage for the requested attribute.
   * @param attributeName the name of the requested attribute
   * @return an {@code Optional} of the attribute
   */
  Optional<Object> getAttribute(String attributeName);

  /**
   * Assigns a value to a given key in the system storage.
   * @param attributeName the key
   * @param value the value to store
   */
  void setAttribute(String attributeName, Object value);

  /**
   * Removes an attribute from the system storage.
   * @param attributeName the name of the attribute
   */
  void removeAttribute(String attributeName);

  /**
   * Gets the owner of the storage.
   * @return the owner player
   */
  Player getOwner();
}
