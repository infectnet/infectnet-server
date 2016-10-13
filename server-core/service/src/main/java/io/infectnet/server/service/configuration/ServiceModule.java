package io.infectnet.server.service.configuration;

import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.persistence.configuration.PersistenceModule;
import io.infectnet.server.service.ConverterService;
import io.infectnet.server.service.TokenService;
import io.infectnet.server.service.UserService;
import io.infectnet.server.service.impl.ConverterServiceImpl;
import io.infectnet.server.service.impl.TokenServiceImpl;
import io.infectnet.server.service.impl.UserServiceImpl;
import io.infectnet.server.service.impl.converter.TokenDtoConverterImpl;
import io.infectnet.server.service.impl.converter.TokenEntityConverterImpl;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module(includes = PersistenceModule.class)
public class ServiceModule {

  @Provides
  @Singleton
  public ConverterService providesConverterService() {
    ConverterService converterService = new ConverterServiceImpl();
    converterService.addConverterMapping(new TokenDtoConverterImpl());
    converterService.addConverterMapping(new TokenEntityConverterImpl());

    return converterService;
  }

  @Provides
  @Singleton
  public TokenService providesTokenService(TokenStorage tokenStorage,
                                           ConverterService converterService) {
    return new TokenServiceImpl(tokenStorage, converterService);
  }

  @Provides
  @Singleton
  public UserService providesUserService() {
    return new UserServiceImpl();
  }

}
