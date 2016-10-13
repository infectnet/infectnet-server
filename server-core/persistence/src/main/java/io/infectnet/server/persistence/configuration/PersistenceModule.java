package io.infectnet.server.persistence.configuration;

import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.persistence.UserStorage;
import io.infectnet.server.persistence.impl.InMemoryTokenStorageImpl;
import io.infectnet.server.persistence.impl.InMemoryUserStorageImpl;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

  @Provides
  @Singleton
  public TokenStorage providesTokenStorage() {
    return new InMemoryTokenStorageImpl();
  }

  @Provides
  @Singleton
  public UserStorage providesUserStorage() {
    return new InMemoryUserStorageImpl();
  }

}
