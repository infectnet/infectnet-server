package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;
import io.infectnet.server.engine.entity.component.TypeComponent;

public class EntityWrapperRepositoryImpl implements EntityWrapperRepository {
  @Override
  public <T extends EntityWrapper> void registerFactoryForType(TypeComponent typeComponent,
                                                               EntityWrapperFactory<T> wrapperFactory) {
    
  }

  @Override
  public EntityWrapperFactory<? extends EntityWrapper> getFactoryForType(
      TypeComponent typeComponent) {
    return null;
  }

  @Override
  public <T extends EntityWrapper> T wrapEntity(Entity entity) {
    return null;
  }
}
