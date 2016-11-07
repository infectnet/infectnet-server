package io.infectnet.server.engine.entity;

/**
 * This class represents a generic entity in the game world. It does not contain behaviour, nor
 * actual data, instead it consists of components.
 */
public class Entity {
  private HealthComponent healthComponent;

  private TypeComponent typeComponent;

  private Entity() {
    /**
     * Cannot be constructed from outside, just using the Builder.
     */
  }

  public HealthComponent getHealthComponent() {
    return healthComponent;
  }

  public TypeComponent getTypeComponent() {
    return typeComponent;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder class for {@code Entity} instances.
   */
  public static class Builder {
    private HealthComponent healthComponent;

    private TypeComponent typeComponent;

    public Builder healthComponent(HealthComponent component) {
      this.healthComponent = component;

      return this;
    }

    public Builder typeComponent(TypeComponent component) {
      this.typeComponent = component;

      return this;
    }

    public Entity build() {
      Entity entity = new Entity();

      entity.healthComponent = this.healthComponent;

      entity.typeComponent = this.typeComponent;

      return entity;
    }
  }
}
