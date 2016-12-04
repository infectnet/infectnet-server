package io.infectnet.server.engine.content.system.inventory;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.RequestOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;

public class InventoryManagementSystem extends RequestOnlyProcessor {

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue.addListener(InventoryModificationRequest.class, this::consumeInventoryRequest);
  }

  private void consumeInventoryRequest(Request request) {
    InventoryModificationRequest inventoryModificationRequest =
        (InventoryModificationRequest) request;

    // This Optional.get() is safe to do, because InventoryRequests always have targets.
    // This is enforced by the constructor.
    Entity modificationTarget = inventoryModificationRequest.getTarget().get();

    String itemName = inventoryModificationRequest.getItemName();

    int modificationNumber = inventoryModificationRequest.getModificationNumber();

    int actualModification;

    if (modificationNumber >= 0) {
      actualModification = Math.min(
          modificationTarget.getInventoryComponent().getFreeCapacity(),
          modificationNumber
      );

    } else {
      actualModification = -1 * Math.min(
          modificationTarget.getInventoryComponent().getInventoryElement(itemName),
          Math.abs(modificationNumber)
      );

    }

    modificationTarget.getInventoryComponent()
        .modifyQuantity(inventoryModificationRequest.getItemName(), actualModification);
  }
}
