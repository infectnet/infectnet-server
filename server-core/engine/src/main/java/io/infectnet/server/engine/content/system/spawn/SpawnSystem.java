package io.infectnet.server.engine.content.system.spawn;


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

public class SpawnSystem implements ProcessorSystem {

  private final ListenableQueue<Request> requestQueue;

  private final TypeRepository typeRepository;

  private final EntityManager entityManager;

  private final World world;

  public SpawnSystem(ListenableQueue<Request> requestQueue, TypeRepository typeRepository,
                     EntityManager entityManager, World world) {
    this.requestQueue = requestQueue;
    this.typeRepository = typeRepository;
    this.entityManager = entityManager;
    this.world = world;
  }

  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    actionQueue.addListener(SpawnAction.class, this::consumeSpawnAction);
  }

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue.addListener(SpawnRequest.class, this::consumeSpawnRequest);
  }

  private void consumeSpawnAction(Action action) {
    SpawnAction spawnAction = (SpawnAction) action;

    Optional<TypeComponent> entityTypeComponent =
        typeRepository.getTypeByName(spawnAction.getEntityType());

    entityTypeComponent.ifPresent((typeComponent) -> {
      requestQueue.add(new SpawnRequest(spawnAction.getSource(), spawnAction, typeComponent));
    });

    //TODO: incorrect type name was given?
  }

  private void consumeSpawnRequest(Request request) {
    SpawnRequest spawnRequest = (SpawnRequest) request;

    // This Optional.get() is safe to do, because SpawnRequests always have targets.
    // This is enforced by the constructor.
    Entity spawnerEntity = spawnRequest.getTarget().get();

    // TODO: correct spawn position
    Position spawnPosition = spawnerEntity.getPositionComponent().getPosition().stepSouth();
    Entity spawnedEntity = spawnRequest.getEntityType().createEntityOfType();

    spawnedEntity.getOwnerComponent().setOwner(spawnerEntity.getOwnerComponent().getOwner());
    spawnedEntity.getPositionComponent().setPosition(spawnPosition);

    entityManager.addEntity(spawnedEntity);
    world.setEntityOnPosition(spawnedEntity, spawnPosition);
  }

}
