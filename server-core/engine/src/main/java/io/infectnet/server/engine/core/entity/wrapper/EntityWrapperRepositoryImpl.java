package io.infectnet.server.engine.core.entity.wrapper;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityWrapperRepositoryImpl implements EntityWrapperRepository {

  private static final Logger logger = LoggerFactory.getLogger(EntityWrapperRepositoryImpl.class);

  private final Map<TypeComponent, EntityWrapperFactory<? extends EntityWrapper>> factoryMap;

  public EntityWrapperRepositoryImpl() {
    this.factoryMap = new HashMap<>();
  }

  @Override
  public <T extends EntityWrapper> void registerFactoryForType(TypeComponent typeComponent,
                                                               EntityWrapperFactory<T> wrapperFactory) {
    factoryMap.put(typeComponent, wrapperFactory);

    logger.info("Registered EntityWrapperFactory for: {}", typeComponent);
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
