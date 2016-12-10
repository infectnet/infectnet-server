package io.infectnet.server.engine.content.system.movement;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.Tile;
import io.infectnet.server.engine.core.world.World;

import java.util.List;

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

    Position startPosition = movementAction.getSource().getPositionComponent().getPosition();
    Position targetPosition = movementAction.getTargetPosition();

    // If the target position occupied and we are next to it, don't move.
    if (world.getTileByPosition(targetPosition).isBlockedOrOccupied()
        && startPosition.getNeighbours().contains(targetPosition)) {
      return;
    }
    List<Tile> path = world.findPath(startPosition, targetPosition);

    // Because the path is returned in reversed order we need the last but one position.
    // The last position in the path is the entity's current position.
    Position nextPosition = path.get(path.size() - 2).getPosition();

    requestQueue.add(new MovementRequest(movementAction.getSource(), movementAction, nextPosition));
  }

  private void consumeMovementRequest(Request request) {
    MovementRequest movementRequest = (MovementRequest) request;

    // This Optional.get() is safe to do, because MovementRequests always have targets.
    // This is enforced by the constructor.
    Entity movementTarget = movementRequest.getTarget().get();

    if (!world.getTileByPosition(movementRequest.getPosition()).isBlockedOrOccupied()) {
      world.setEntityOnPosition(null, movementTarget.getPositionComponent().getPosition());

      world.setEntityOnPosition(movementTarget, movementRequest.getPosition());

      movementTarget.getPositionComponent().setPosition(movementRequest.getPosition());
    }

  }
}
