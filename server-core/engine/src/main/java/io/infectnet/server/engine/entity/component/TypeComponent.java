package io.infectnet.server.engine.entity.component;

import io.infectnet.server.engine.entity.Category;
import io.infectnet.server.engine.entity.Entity;

import java.util.Optional;

/**
 * Abstract base class for entity types. The actual type of an {@link Entity} instance is determined
 * by its {@code TypeComponent}.
 */
public abstract class TypeComponent {
  private final TypeComponent parent;

  private final Category category;

  private final String name;

  public TypeComponent(Category category, String name) {
    this(null, category, name);
  }

  public TypeComponent(TypeComponent parent, Category category, String name) {
    this.parent = parent;

    this.category = category;

    this.name = name;
  }

  public abstract Entity createEntityOfType();

  public Optional<TypeComponent> getParent() {
    return Optional.ofNullable(parent);
  }

  public Category getCategory() {
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
