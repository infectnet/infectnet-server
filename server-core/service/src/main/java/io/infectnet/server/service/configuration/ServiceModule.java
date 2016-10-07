package io.infectnet.server.service.configuration;

import dagger.Module;
import dagger.Provides;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.persistence.configuration.PersistenceModule;
import io.infectnet.server.service.TokenService;
import io.infectnet.server.service.UserService;
import io.infectnet.server.service.impl.TokenServiceImpl;
import io.infectnet.server.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;

import javax.inject.Singleton;

@Module(includes = PersistenceModule.class)
public class ServiceModule {

    @Provides
    @Singleton
    public ModelMapper providesModelMapper() {
        return new ModelMapper();
    }

    @Provides
    @Singleton
    public TokenService providesTokenService(TokenStorage tokenStorage, ModelMapper modelMapper) {
        return new TokenServiceImpl(tokenStorage, modelMapper);
    }

    @Provides
    @Singleton
    public UserService providesUserService() {
        return new UserServiceImpl();
    }

}
