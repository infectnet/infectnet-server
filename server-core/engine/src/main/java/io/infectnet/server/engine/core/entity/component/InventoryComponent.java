package io.infectnet.server.engine.core.entity.component;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryComponent {

  private final int capacity;

  private final Map<String, Integer> inventoryMap;

  private int freeCapacity;

  public InventoryComponent(int capacity) {
    this.capacity = capacity;
    freeCapacity = capacity;
    this.inventoryMap = new HashMap<>();
  }

  public Optional<Integer> getInventoryElement(String inventoryElement) {
    return Optional.ofNullable(inventoryMap.get(inventoryElement));
  }

  public int addQuantity(String inventoryElement, Integer num) {
    int actualAddition = Math.min(freeCapacity, num);

    if (actualAddition > 0) {
      inventoryMap
          .put(inventoryElement, inventoryMap.getOrDefault(inventoryElement, 0) + actualAddition);

      freeCapacity -= actualAddition;
    }

    return actualAddition;
  }

  public int removeQuantity(String inventoryElement, Integer num) {
    int actualRemoval = Math.max(num, inventoryMap.getOrDefault(inventoryElement, 0));

    if (actualRemoval > 0) {
      //TODO removal
    }

    return actualRemoval;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getFreeCapacity() {
    return freeCapacity;
  }

}
