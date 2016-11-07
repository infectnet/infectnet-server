package io.infectnet.server.engine.entity;

import java.util.Optional;

/**
 * Abstract base class for entity types. The actual type of an {@link Entity} instance is determined
 * by its {@code TypeComponent}.
 */
public abstract class TypeComponent {
  private final TypeComponent parent;

  private final String category;

  private final String name;

  public TypeComponent(String category, String name) {
    this(null, category, name);
  }

  public TypeComponent(TypeComponent parent, String category, String name) {
    this.parent = parent;

    this.category = category;

    this.name = name;
  }

  public abstract Entity createEntityOfType();

  public Optional<TypeComponent> getParent() {
    return Optional.ofNullable(parent);
  }

  public String getCategory() {
    return category;
  }

  public String getName() {
    return name;
  }

  public boolean isDescendantOf(TypeComponent baseType) {
    Optional<TypeComponent> parentOptional = this.getParent();

    while (parentOptional.isPresent()) {
      if (parentOptional.get().equals(baseType)) {
        return true;
      }

      parentOptional = parentOptional.get().getParent();
    }

    return false;
  }
}
