package io.infectnet.server.engine.entity.component;

/**
 * Component storing an {@link io.infectnet.server.engine.entity.Entity}'s health points.
 */
public class HealthComponent {
  private static final int DEFAULT_HEALTH = 0;

  private int health;

  public HealthComponent() {
    this(DEFAULT_HEALTH);
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
