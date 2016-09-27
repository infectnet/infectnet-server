package io.infectnet.server.persistence.configuration;

import dagger.Module;
import dagger.Provides;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.persistence.UserStorage;
import io.infectnet.server.persistence.impl.InMemoryTokenStorageImpl;
import io.infectnet.server.persistence.impl.InMemoryUserStorageImpl;

import javax.inject.Singleton;

@Module
public class PersistenceModule {

    @Provides
    @Singleton
    TokenStorage providesTokenStorage() {
        return new InMemoryTokenStorageImpl();
    }

    @Provides
    @Singleton
    UserStorage providesUserStorage() {
        return new InMemoryUserStorageImpl();
    }

}
