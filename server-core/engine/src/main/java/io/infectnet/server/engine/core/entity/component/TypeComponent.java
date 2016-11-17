package io.infectnet.server.engine.core.entity.component;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;

import java.util.Objects;
import java.util.Optional;

/**
 * Abstract base class for entity types. The actual type of an
 * {@link io.infectnet.server.engine.core.entity.Entity} instance is determined by its
 * {@code TypeComponent}.
 */
public abstract class TypeComponent {
  private final TypeComponent parent;

  private final Category category;

  private final String name;

  /**
   * Constructs a new instance with the specified category and name and parent set to {@code null}.
   * @param category the category
   * @param name the name
   * @throws NullPointerException if any of the parameters is {@code null}
   */
  public TypeComponent(Category category, String name) {
    this(null, category, name);
  }

  /**
   * Constructs a new instance with the specified attributes.
   * @param parent the parent type
   * @param category the category
   * @param name the name
   * @throws NullPointerException if any of the parameters is {@code null}
   */
  public TypeComponent(TypeComponent parent, Category category, String name) {
    this.parent = parent;

    this.category = Objects.requireNonNull(category);

    this.name = Objects.requireNonNull(name);
  }

  /**
   * Constructs a new blank {@code Entity} with its {@code TypeComponent} set to this instance. May
   * also set other fields to type-default values.
   * @return a new {@code Entity}
   */
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

  /**
   * Determines whether this {@code TypeComponent} is a descendant of the passed
   * {@code TypeComponent}.
   * @param baseType the type to check against
   * @return whether this {@code TypeComponent} is a descendant of the passed {@code TypeComponent}
   */
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

  @Override
  public String toString() {
    return "TypeComponent{" +
        "parent=" + parent +
        ", category=" + category +
        ", name='" + name + '\'' +
        '}';
  }
}
