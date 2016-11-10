package io.infectnet.server.engine.player;

import java.util.Optional;

/**
 * Interface for querying {@link Player} related data. Implementations of this interface <b>must</b>
 * be thread-safe to ensure multiple thread creating and querying {@code Player} data simultaneously.
 */
public interface PlayerService {
  /**
   * Creates a new {@code Player} instance with the specified username and returns it. If there is
   * a {@code Player} with the same username or some other error happened during the process,
   * an empty {@code Optional} is returned.
   * @param username the username of the {@code Player}
   * @return an {@code Optional} with the newly created {@code Player} or an empty {@code Optional}
   * upon failure.
   */
  Optional<Player> createPlayer(String username);

  /**
   * Gets the {@code Player} with the specified username or an empty {@code Optional} if there's no
   * {@code Player} with this username.
   * @param username the username of the {@code Player}
   * @return upon success an {@code Optional} with the {@code Player} instance, an empty
   * {@code Optional} otherise
   */
  Optional<Player> getPlayerByUsername(String username);

  /**
   * Checks whether the passed {@code Player} is observed. A {@code Player} is observed if
   * the game loop must produce status updates of that {@code Player} that should be emitted to the
   * outside world.
   * @param player the {@code Player}
   * @return whether the {@code Player} is observed
   */
  boolean isPlayerObserved(Player player);

  /**
   * Sets the {@code Player} as observed. For the meaning of being observed, please refer to
   * {@link #isPlayerObserved(Player)}.
   * @param player the {@code Player} to be set as observed
   */
  void setPlayerAsObserved(Player player);

  /**
   * Removes the {@code Player} from the list of observed {@code Player}s. For the meaning of being
   * observed, please refer to {@link #isPlayerObserved(Player)}.
   * @param player the {@code Player} to be removed
   */
  void removePlayerFromObserved(Player player);
}
