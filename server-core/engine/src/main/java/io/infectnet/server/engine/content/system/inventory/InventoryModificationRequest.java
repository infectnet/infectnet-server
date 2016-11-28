package io.infectnet.server.engine.content.system.inventory;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;

public class InventoryModificationRequest extends Request {

  private String itemName;

  private int modificationNumber;

  public InventoryModificationRequest(Entity target,
                                      Action origin,
                                      String itemName,
                                      int modificationNumber) {
    super(target, origin);

    this.itemName = itemName;
    this.modificationNumber = modificationNumber;
  }

  public String getItemName() {
    return itemName;
  }

  public int getModificationNumber() {
    return modificationNumber;
  }
}
