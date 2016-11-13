package io.infectnet.server.engine.configuration.core;

import io.infectnet.server.engine.entity.EntityManager;
import io.infectnet.server.engine.entity.EntityManagerImpl;
import io.infectnet.server.engine.entity.type.TypeRepository;
import io.infectnet.server.engine.entity.type.TypeRepositoryImpl;
import io.infectnet.server.engine.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.entity.wrapper.EntityWrapperRepositoryImpl;

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
