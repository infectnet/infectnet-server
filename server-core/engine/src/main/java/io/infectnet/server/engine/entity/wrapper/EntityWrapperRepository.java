package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;
import io.infectnet.server.engine.entity.component.TypeComponent;

public interface EntityWrapperRepository {
  <T extends EntityWrapper> void registerFactoryForType(TypeComponent typeComponent,
                                                        EntityWrapperFactory<T> wrapperFactory);

  EntityWrapperFactory<? extends EntityWrapper> getFactoryForType(TypeComponent typeComponent);

  <T extends EntityWrapper> T wrapEntity(Entity entity);
}
