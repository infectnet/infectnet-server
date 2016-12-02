package io.infectnet.server.engine.core.entity.component;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryComponent {

  private static final int DEFAULT_CAPACITY = 0;

  private final int capacity;

  private final Map<String, Integer> inventoryMap;

  private int freeCapacity;

  public InventoryComponent() {
    this(DEFAULT_CAPACITY);
  }

  public InventoryComponent(int capacity) {
    this.capacity = capacity;
    freeCapacity = capacity;
    this.inventoryMap = new HashMap<>();
  }

  public Optional<Integer> getInventoryElement(String itemName) {
    return Optional.ofNullable(inventoryMap.get(itemName));
  }

  public void modifyQuantity(String itemName, Integer num) {
    inventoryMap
        .put(itemName, inventoryMap.getOrDefault(itemName, 0) + num);

    freeCapacity -= num;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getFreeCapacity() {
    return freeCapacity;
  }

}
