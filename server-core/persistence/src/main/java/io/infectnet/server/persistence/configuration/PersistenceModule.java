package io.infectnet.server.persistence.configuration;

import io.infectnet.server.persistence.token.TokenStorage;
import io.infectnet.server.persistence.user.UserStorage;
import io.infectnet.server.persistence.token.InMemoryTokenStorageImpl;
import io.infectnet.server.persistence.user.InMemoryUserStorageImpl;

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
