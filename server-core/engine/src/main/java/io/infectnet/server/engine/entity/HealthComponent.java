package io.infectnet.server.engine.entity;

/**
 * Component storing an {@link Entity}'s health points.
 */
public class HealthComponent {
  private int health;

  public HealthComponent() {
    this(0);
  }

  public HealthComponent(int health) {
    this.health = health;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }
}
