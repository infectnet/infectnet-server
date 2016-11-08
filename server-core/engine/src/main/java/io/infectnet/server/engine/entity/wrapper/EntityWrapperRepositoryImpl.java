package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;
import io.infectnet.server.engine.entity.component.TypeComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityWrapperRepositoryImpl implements EntityWrapperRepository {
  private final Map<TypeComponent, EntityWrapperFactory<? extends EntityWrapper>> factoryMap;

  public EntityWrapperRepositoryImpl() {
    this.factoryMap = new HashMap<>();
  }

  @Override
  public <T extends EntityWrapper> void registerFactoryForType(TypeComponent typeComponent,
                                                               EntityWrapperFactory<T> wrapperFactory) {
    factoryMap.put(typeComponent, wrapperFactory);
  }

  @Override
  public final EntityWrapperFactory<? extends EntityWrapper> getFactoryForType(
      TypeComponent typeComponent) {
    EntityWrapperFactory<? extends EntityWrapper> factory =
        factoryMap.get(Objects.requireNonNull(typeComponent));

    if (factory == null) {
      throw new FactoryNotFoundException(typeComponent);
    }

    return factory;
  }

  @Override
  public EntityWrapper wrapEntity(Entity entity) {
    return getFactoryForType(entity.getTypeComponent()).wrapEntity(entity);
  }
}
