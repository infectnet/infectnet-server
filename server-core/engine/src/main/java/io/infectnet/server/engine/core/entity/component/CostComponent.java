package io.infectnet.server.engine.core.entity.component;


public class CostComponent {

  private static final int DEFAULT_COST = 0;

  private final int cost;

  public CostComponent() {
    this(DEFAULT_COST);
  }

  public CostComponent(int cost) {
    this.cost = cost;
  }

  public int getCost() {
    return cost;
  }

}
