package io.infectnet.server.engine.content.system.infect;


import io.infectnet.server.engine.content.system.inventory.InventoryModificationRequest;
import io.infectnet.server.engine.content.system.kill.KillRequest;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ActionOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.World;

public class InfectSystem extends ActionOnlyProcessor {

  private static final String ITEM_NAME = "bit";
  private static final int MODIFICATION_NUMBER = 1;

  private final ListenableQueue<Request> requestQueue;
  private final World world;

  public InfectSystem(ListenableQueue<Request> requestQueue, World world) {
    this.requestQueue = requestQueue;
    this.world = world;
  }

  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    actionQueue.addListener(InfectAction.class, this::consumeInfectAction);
  }

  private void consumeInfectAction(Action action) {
    InfectAction infectAction = (InfectAction) action;

    Entity infectorEntity = infectAction.getSource();
    Entity resourceEntity = infectAction.getResource();

    if (world.neighboursOf(infectorEntity).contains(resourceEntity)) {
      requestQueue.add(
          new InventoryModificationRequest(infectorEntity, action, ITEM_NAME, MODIFICATION_NUMBER));

      if (resourceEntity.getInventoryComponent().getInventoryElement(ITEM_NAME)
          - MODIFICATION_NUMBER <= 0) {
        requestQueue.add(new KillRequest(resourceEntity, infectAction));

      } else {
        requestQueue.add(new InventoryModificationRequest(resourceEntity, action,
            ITEM_NAME, -1 * MODIFICATION_NUMBER));
      }
    }

  }

}
