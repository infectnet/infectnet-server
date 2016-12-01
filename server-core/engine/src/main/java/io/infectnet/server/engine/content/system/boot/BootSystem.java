package io.infectnet.server.engine.content.system.boot;

import io.infectnet.server.engine.content.system.creation.EntityCreationRequest;
import io.infectnet.server.engine.content.system.movement.MovementRequest;
import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ActionOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.World;

import java.util.Optional;

public class BootSystem extends ActionOnlyProcessor {

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

  private void consumeBootAction(Action action) {
    BootAction bootAction = (BootAction) action;

    Optional<TypeComponent> buildingType =
        typeRepository.getTypeByName(bootAction.getBuildingType())
            .filter(type -> type.getCategory() == Category.BUILDING);

    buildingType.ifPresent(typeComponent -> {
      requestQueue.add(new MovementRequest(bootAction.getSource(), bootAction,
          bootAction.getSource().getPositionComponent().getPosition().stepSouth()));

      requestQueue.add(new EntityCreationRequest(bootAction.getSource(), action, typeComponent));

    });

    //TODO: incorrect type name was given?

  }

}
