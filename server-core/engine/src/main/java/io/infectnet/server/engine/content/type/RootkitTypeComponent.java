package io.infectnet.server.engine.content.type;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.HealthComponent;
import io.infectnet.server.engine.core.entity.component.NullInventoryComponent;
import io.infectnet.server.engine.core.entity.component.OwnerComponent;
import io.infectnet.server.engine.core.entity.component.PositionComponent;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.component.ViewComponent;


public class RootkitTypeComponent extends TypeComponent {

  public static final String TYPE_NAME = "Rootkit";

  private static final int INITIAL_HEALTH = 50;
  private static final int VIEW_RADIUS = 5;

  public RootkitTypeComponent() {
    super(Category.BUILDING, TYPE_NAME);
  }

  @Override
  public Entity createEntityOfType() {
    return Entity.builder()
        .typeComponent(this)
        .ownerComponent(new OwnerComponent())
        .healthComponent(new HealthComponent(INITIAL_HEALTH))
        .positionComponent(new PositionComponent())
        .viewComponent(new ViewComponent(VIEW_RADIUS))
        .inventoryComponent(NullInventoryComponent.getInstance())
        .build();
  }
}
