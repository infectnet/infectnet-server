package io.infectnet.server.engine.content.type;


import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.HealthComponent;
import io.infectnet.server.engine.core.entity.component.NullViewComponent;
import io.infectnet.server.engine.core.entity.component.OwnerComponent;
import io.infectnet.server.engine.core.entity.component.PositionComponent;
import io.infectnet.server.engine.core.entity.component.TypeComponent;

public class WormTypeComponent extends TypeComponent{

  private static final String TYPE_NAME = "Worm";

  private static final int INITIAL_HEALTH = 15;

  public WormTypeComponent() {
    super(Category.WORKER,  TYPE_NAME);
  }

  @Override
  public Entity createEntityOfType() {
    return Entity.builder()
        .typeComponent(this)
        .ownerComponent(new OwnerComponent())
        .healthComponent(new HealthComponent(INITIAL_HEALTH))
        .positionComponent(new PositionComponent())
        .viewComponent(NullViewComponent.getInstance())
        .build();
  }
}
