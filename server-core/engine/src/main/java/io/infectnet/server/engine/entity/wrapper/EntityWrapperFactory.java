package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.Entity;

public interface EntityWrapperFactory<T extends EntityWrapper> {
  T wrapEntity(Entity entity);
}
