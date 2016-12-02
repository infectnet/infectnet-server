package io.infectnet.server.engine.content.system.creation;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.RequestOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

import java.util.Optional;


public class EntityCreatorSystem extends RequestOnlyProcessor {

  private final EntityManager entityManager;

  private final World world;

  public EntityCreatorSystem(EntityManager entityManager, World world) {
    this.entityManager = entityManager;
    this.world = world;
  }

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue.addListener(EntityCreationRequest.class, this::consumeEntityCreationRequest);
  }

  private void consumeEntityCreationRequest(Request request) {
    EntityCreationRequest entityCreationRequest = (EntityCreationRequest) request;

    // This Optional.get() is safe to do, because EntityCreationRequests always have targets.
    // This is enforced by the constructor.
    Entity creatorEntity = entityCreationRequest.getTarget().get();

    Position createPosition = getCreatePosition(creatorEntity);

    if (createPosition != null) {

      //TODO: spawn cost

      Entity createdEntity =
          createEntity(creatorEntity, entityCreationRequest.getEntityType(), createPosition);

      entityManager.addEntity(createdEntity);
      world.setEntityOnPosition(createdEntity, createPosition);
    }
  }

  private Entity createEntity(Entity creatorEntity,
                              TypeComponent entityType,
                              Position createPosition) {

    Entity createdEntity = entityType.createEntityOfType();

    createdEntity.getOwnerComponent().setOwner(creatorEntity.getOwnerComponent().getOwner());
    createdEntity.getPositionComponent().setPosition(createPosition);

    return createdEntity;
  }

  private Position getCreatePosition(Entity creatorEntity) {
    Position createPosition = null;

    if (creatorEntity.getTypeComponent().getCategory() == Category.BUILDING) {

      Optional<Position> freePosition =
          getFreePositionAround(creatorEntity.getPositionComponent().getPosition());

      if (freePosition.isPresent()) {
        createPosition = freePosition.get();
      }

    } else {
      createPosition = creatorEntity.getPositionComponent().getPosition();
    }

    return createPosition;
  }

  private Optional<Position> getFreePositionAround(Position position) {
    Position[] neighbours = {
        position.stepNorth(),
        position.stepNorth().stepEast(),
        position.stepEast(),
        position.stepEast().stepSouth(),
        position.stepSouth(),
        position.stepSouth().stepWest(),
        position.stepWest(),
        position.stepWest().stepNorth()
    };

    for (Position n : neighbours) {
      if (!world.getTileByPosition(n).isBlockedOrOccupied()) {
        return Optional.of(n);
      }
    }

    return Optional.empty();
  }

}
