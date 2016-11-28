package io.infectnet.server.engine.core.configuration;

import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.EntityManagerImpl;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.entity.type.TypeRepositoryImpl;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepositoryImpl;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.Multibinds;

@Module
public abstract class EntityModule {
  @Multibinds
  abstract Map<String, EntityWrapperFactory<? extends EntityWrapper>> bindsEmptyWrapperFactoryMap();

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

  @Provides
  @ElementsIntoSet
  public static Set<TypeComponent> providesDefaultEmptyTypeComponentSet() {
    return Collections.emptySet();
  }
}
