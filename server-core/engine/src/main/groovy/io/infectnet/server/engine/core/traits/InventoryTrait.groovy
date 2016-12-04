package io.infectnet.server.engine.core.traits

trait InventoryTrait {

  /**
   * Returns the wrapped {@link io.infectnet.server.engine.core.entity.Entity}'s maximum inventory capacity.
   * @return the max inventory capacity
   */
  int getMaxInventoryCapacity() {
    return this.wrappedEntity.getInventoryComponent().getCapacity();
  }

  /**
   * Returns the wrapped {@link io.infectnet.server.engine.core.entity.Entity}'s free inventory capacity.
   * @return the free inventory capacity
   */
  int getFreeInventoryCapacity() {
    return this.wrappedEntity.getInventoryComponent().getFreeCapacity();
  }

  /**
   * Returns the wrapped {@link io.infectnet.server.engine.core.entity.Entity}'s whole inventory.
   * @return the whole inventory
   */
  Map<String, Integer> getInventory() {
    return this.wrappedEntity.getInventoryComponent().getInventory();
  }

  /**
   * Returns an item number for the given item name owned by
   * the wrapped {@link io.infectnet.server.engine.core.entity.Entity}.
   * @param itemName the name of the requested item
   * @return the number of items
   */
  int getItem(String itemName) {
    return this.wrappedEntity.getInventoryComponent().getInventoryElement(itemName);
  }

}
