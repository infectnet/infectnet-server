package io.infectnet.server.engine.content.system.boot;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

import java.util.Optional;

public class BootSystem implements ProcessorSystem {

  private final ListenableQueue<Request> requestQueue;

  private final TypeRepository typeRepository;

  private final EntityManager entityManager;

  private final World world;

  public BootSystem(ListenableQueue<Request> requestQueue, TypeRepository typeRepository,
                    EntityManager entityManager, World world) {
    this.requestQueue = requestQueue;
    this.typeRepository = typeRepository;
    this.entityManager = entityManager;
    this.world = world;
  }

  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    actionQueue.addListener(BootAction.class, this::consumeBootAction);
  }

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue.addListener(BootRequest.class, this::consumeBootRequest);
  }

  private void consumeBootAction(Action action) {
    BootAction bootAction = (BootAction) action;

    if (isEnoughSpaceAvailable(bootAction.getSource().getPositionComponent().getPosition())) {
      Optional<TypeComponent> buildingType =
          typeRepository.getTypeByName(bootAction.getBuildingType())
              .filter(type -> type.getCategory() == Category.BUILDING);

      buildingType.ifPresent(typeComponent -> {
        requestQueue.add(new BootRequest(bootAction.getSource(), action, typeComponent));
      });

      //TODO: incorrect type name was given?
    }

    //TODO: not enough space?

  }

  private void consumeBootRequest(Request request) {
    BootRequest bootRequest = (BootRequest) request;

    // This Optional.get() is safe to do, because BootRequests always have targets.
    // This is enforced by the constructor.
    Entity booterEntity = bootRequest.getTarget().get();

    Position bootPosition = booterEntity.getPositionComponent().getPosition();
    Entity bootedEntity = bootRequest.getBuildingType().createEntityOfType();

    bootedEntity.getOwnerComponent().setOwner(booterEntity.getOwnerComponent().getOwner());
    bootedEntity.getPositionComponent().setPosition(bootPosition);

    world.setEntityOnPosition(booterEntity,
        booterEntity.getPositionComponent().getPosition().stepSouth());

    entityManager.addEntity(bootedEntity);
    world.setEntityOnPosition(bootedEntity, bootPosition);
  }

  private boolean isEnoughSpaceAvailable(Position position) {
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
      if (world.getTileByPosition(n).isBlockedOrOccupied()) {
        return false;
      }
    }

    return true;
  }

}
