package io.infectnet.server.engine.core.configuration;

import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.EntityManagerImpl;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.entity.type.TypeRepositoryImpl;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepositoryImpl;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class EntityModule {
  @Provides
  @Singleton
  public static EntityManager providesEntityManager() {
    return new EntityManagerImpl();
  }

  @Provides
  @Singleton
  public static EntityWrapperRepository providesEntityWrapperRepository() {
    return new EntityWrapperRepositoryImpl();
  }

  @Provides
  @Singleton
  public static TypeRepository providesTypeRepository() {
    return new TypeRepositoryImpl();
  }
}
