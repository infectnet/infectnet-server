package io.infectnet.server.engine.content.type;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.InventoryComponent;
import io.infectnet.server.engine.core.entity.component.NullHealthComponent;
import io.infectnet.server.engine.core.entity.component.NullViewComponent;
import io.infectnet.server.engine.core.entity.component.OwnerComponent;
import io.infectnet.server.engine.core.entity.component.PositionComponent;
import io.infectnet.server.engine.core.entity.component.TypeComponent;

public class BitResourceTypeComponent extends TypeComponent {

  public static final String TYPE_NAME = "Bit";

  private static final int INITIAL_HEALTH = 50;
  private static final int INVENTORY_CAPACITY = 300;

  public BitResourceTypeComponent() {
    super(Category.RESOURCE, TYPE_NAME);
  }

  @Override
  public Entity createEntityOfType() {
    return Entity.builder()
        .typeComponent(this)
        .healthComponent(NullHealthComponent.getInstance())
        .ownerComponent(new OwnerComponent())
        .positionComponent(new PositionComponent())
        .viewComponent(NullViewComponent.getInstance())
        .inventoryComponent(new InventoryComponent(INVENTORY_CAPACITY))
        .build();
  }

}
