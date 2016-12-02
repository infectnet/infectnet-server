package io.infectnet.server.engine.core.entity.component;


public class NullInventoryComponent extends InventoryComponent {

  private static NullInventoryComponent instance = new NullInventoryComponent();

  public static NullInventoryComponent getInstance() {
    return instance;
  }

}
