package io.infectnet.server.engine.content.type;


import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.CostComponent;
import io.infectnet.server.engine.core.entity.component.HealthComponent;
import io.infectnet.server.engine.core.entity.component.InventoryComponent;
import io.infectnet.server.engine.core.entity.component.NullViewComponent;
import io.infectnet.server.engine.core.entity.component.OwnerComponent;
import io.infectnet.server.engine.core.entity.component.PositionComponent;
import io.infectnet.server.engine.core.entity.component.TypeComponent;

public class TrojanTypeComponent extends TypeComponent {

  public static final String TYPE_NAME = "Trojan";

  private static final int INITIAL_HEALTH = 25;
  private static final int INVENTORY_CAPACITY = 5;
  private static final int SPAWN_COST = 10;

  private final CostComponent costComponent;

  public TrojanTypeComponent() {
    super(Category.FIGHTER, TYPE_NAME);

    this.costComponent = new CostComponent(SPAWN_COST);
  }

  @Override
  public Entity createEntityOfType() {
    return Entity.builder()
        .typeComponent(this)
        .viewComponent(NullViewComponent.getInstance())
        .positionComponent(new PositionComponent())
        .healthComponent(new HealthComponent(INITIAL_HEALTH))
        .ownerComponent(new OwnerComponent())
        .inventoryComponent(new InventoryComponent(INVENTORY_CAPACITY))
        .costComponent(this.costComponent)
        .build();
  }

}
