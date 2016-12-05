package io.infectnet.server.engine.core.entity.component;


public class NullCostComponent extends CostComponent {

  private static NullCostComponent instance = new NullCostComponent();

  public static NullCostComponent getInstance() {
    return instance;
  }


}
