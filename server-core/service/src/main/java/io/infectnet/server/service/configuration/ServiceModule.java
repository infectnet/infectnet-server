package io.infectnet.server.service.configuration;

import dagger.Module;
import dagger.Provides;
import io.infectnet.server.persistence.configuration.PersistenceModule;
import io.infectnet.server.service.TokenService;
import io.infectnet.server.service.UserService;
import io.infectnet.server.service.impl.TokenServiceImpl;
import io.infectnet.server.service.impl.UserServiceImpl;

import javax.inject.Singleton;

@Module(includes = PersistenceModule.class)
public class ServiceModule {

    @Provides
    @Singleton
    public TokenService providesTokenService() {
        return new TokenServiceImpl();
    }

    @Provides
    @Singleton
    public UserService providesUserService() {
        return new UserServiceImpl();
    }

}
