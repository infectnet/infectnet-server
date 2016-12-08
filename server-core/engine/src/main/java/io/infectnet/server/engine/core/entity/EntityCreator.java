package io.infectnet.server.engine.core.entity;

import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

import java.util.Objects;

/**
 * Helper class for creating {@link Entity} objects and storing them in a {@link World} and
 * {@link EntityManager} instance.
 */
public class EntityCreator {
  private final EntityManager entityManager;

  private final World world;

  /**
   * Constructs a new instance that will place created {@code Entity} objects in the specified
   * parameters.
   * @param entityManager the {@code EntityManager} that will manage the {@code Entity}
   * @param world the {@code World} the {@code Entity} is placed on
   */
  public EntityCreator(EntityManager entityManager, World world) {
    this.entityManager = entityManager;

    this.world = world;
  }

  /**
   * Creates a new {@code Entity} instance with the given type. The newly created object will have
   * its owner and position set to be the passed parameters and will be added to the {@code World}
   * and the {@code EntityManager} instance this {@code EntityCreator} was constructed with.
   * <p>
   * Note that this method may throw any exception that can occur during the creation process.
   * </p>
   * @param type the type that will construct the new {@code Entity}
   * @param position the starting position of the {@code Entity}
   * @param owner the owner of the {@code Entity}
   * @return the newly created {@code Entity} object
   * @throws NullPointerException If the value of a parameter is {@code null}. In that case, no
   * {@code Entity} will be created.
   */
  public Entity create(TypeComponent type, Position position, Player owner) {
    TypeComponent nonNullType = Objects.requireNonNull(type);
    Position nonNullPosition = Objects.requireNonNull(position);
    Player nonNullOwner = Objects.requireNonNull(owner);

    Entity entity = nonNullType.createEntityOfType();

    entity.getPositionComponent().setPosition(nonNullPosition);
    entity.getOwnerComponent().setOwner(nonNullOwner);

    entityManager.addEntity(entity);

    world.getTileByPosition(nonNullPosition).setEntity(entity);

    return entity;
  }
}
