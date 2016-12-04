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
  Map<String, Object> getResourceMap();

  /**
   * Queries the system storage for the requested resource.
   * @param resourceName the name of the requested resource
   * @return an {@code Optional} of the resource
   */
  Optional<Object> getResource(String resourceName);

  /**
   * Assigns a value to a given key in the system storage.
   * @param resourceName the key
   * @param value the value to store
   */
  void setResource(String resourceName, Object value);

  /**
   * Removes a record from the system storage.
   * @param resourceName the name of the resource
   */
  void removeResource(String resourceName);

  /**
   * Gets the owner of the storage.
   * @return the owner player
   */
  Player getOwner();
}
