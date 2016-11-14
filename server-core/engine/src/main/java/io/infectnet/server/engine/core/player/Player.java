package io.infectnet.server.engine.core.player;

/**
 * Class representing a player and holding its data. {@code Player}s can be identified by their
 * unique username.
 */
public class Player {
  private final String username;

  /**
   * Constructs a new instance using the specified username. The username cannot be changed after
   * the instance's been created.
   * @param username the username of the {@code Player}
   */
  /* package */ Player(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
