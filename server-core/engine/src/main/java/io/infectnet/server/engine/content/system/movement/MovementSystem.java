package io.infectnet.server.engine.content.system.movement;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

public class MovementSystem implements ProcessorSystem {

  private final ListenableQueue<Request> requestQueue;

  private final World world;

  public MovementSystem(
      ListenableQueue<Request> requestQueue, World world) {
    this.requestQueue = requestQueue;
    this.world = world;
  }

  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    actionQueue.addListener(MovementAction.class, this::consumeMovementAction);
  }

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue.addListener(MovementRequest.class, this::consumeMovementRequest);
  }

  private void consumeMovementAction(Action action) {
    MovementAction movementAction = (MovementAction) action;

    // TODO: correct next position given by world
    Position nextPosition = movementAction.getTargetEntity().getPositionComponent().getPosition();

    requestQueue.add(new MovementRequest(movementAction.getSource(), movementAction, nextPosition));
  }

  private void consumeMovementRequest(Request request) {
    MovementRequest movementRequest = (MovementRequest) request;

    // This Optional.get() is safe to do, because MovementRequests always have targets.
    // This is enforced by the constructor.
    Entity movementTarget = movementRequest.getTarget().get();

    world.setEntityOnPosition(null, movementTarget.getPositionComponent().getPosition());

    world.setEntityOnPosition(movementTarget, movementRequest.getPosition().stepSouth());

    movementTarget.getPositionComponent().setPosition(movementRequest.getPosition());
  }
}
