package io.infectnet.server.persistence.configuration;

import dagger.Module;
import dagger.Provides;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.persistence.UserStorage;
import io.infectnet.server.persistence.impl.InMemoryTokenStorageImpl;
import io.infectnet.server.persistence.impl.InMemoryUserStorageImpl;

import javax.inject.Singleton;
import java.util.ArrayList;

@Module
public class PersistenceModule {

    @Provides
    @Singleton
    public TokenStorage providesTokenStorage() {
        return new InMemoryTokenStorageImpl(new ArrayList<>());
    }

    @Provides
    @Singleton
    public UserStorage providesUserStorage() {
        return new InMemoryUserStorageImpl();
    }

}
