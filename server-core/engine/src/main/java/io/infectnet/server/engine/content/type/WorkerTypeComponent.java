package io.infectnet.server.engine.content.type;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.OwnerComponent;
import io.infectnet.server.engine.core.entity.component.TypeComponent;

public class WorkerTypeComponent extends TypeComponent {
  public static final String TYPE_NAME = "Worker";

  public WorkerTypeComponent() {
    super(Category.WORKER, TYPE_NAME);
  }

  @Override
  public Entity createEntityOfType() {
    return Entity.builder()
        .typeComponent(this)
        .ownerComponent(new OwnerComponent())
        .build();
  }
}
