package io.infectnet.server.engine.script.code;

import io.infectnet.server.engine.player.Player;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository interface for {@link Code} storage classes.
 */
public interface CodeRepository {
  /**
   * Adds the specified {@code Code} object associated with the specified {@code Player}.
   * @param player the {@code Player} the {@code Code} belongs to
   * @param code the {@code Code} to be stored
   */
  void addCode(Player player, Code code);

  /**
   * Gets the {@code Code} associated with the specified {@code Player}.
   * @param player the owner of the {@code Code}
   * @return an {@code Optional} containing the associated {@code Code} object or an empty
   * {@code Optional} if the {@code Player} has no {@code Code} stored
   */
  Optional<Code> getCodeByPlayer(Player player);

  /**
   * Gets the collection of all stored {@code Code} objects.
   * @return the collection of all stored {@code Code}s
   */
  Collection<Code> getAllCodes();
}
