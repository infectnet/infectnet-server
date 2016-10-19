package io.infectnet.server.service.configuration;

import io.infectnet.server.persistence.token.TokenStorage;
import io.infectnet.server.persistence.user.UserStorage;
import io.infectnet.server.persistence.configuration.PersistenceModule;
import io.infectnet.server.service.converter.ConverterService;
import io.infectnet.server.service.encrypt.EncrypterService;
import io.infectnet.server.service.encrypt.JBCryptEncrypterService;
import io.infectnet.server.service.token.TokenService;
import io.infectnet.server.service.user.UserService;
import io.infectnet.server.service.converter.ConverterServiceImpl;
import io.infectnet.server.service.token.TokenServiceImpl;
import io.infectnet.server.service.user.UserServiceImpl;
import io.infectnet.server.service.token.mapping.TokenDtoConverterImpl;
import io.infectnet.server.service.token.mapping.TokenEntityConverterImpl;
import io.infectnet.server.service.user.mapping.UserDtoConverterImpl;
import io.infectnet.server.service.user.mapping.UserEntityConverterImpl;

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
    converterService.addConverterMapping(new UserDtoConverterImpl());
    converterService.addConverterMapping(new UserEntityConverterImpl());

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
  public UserService providesUserService(UserStorage userStorage, TokenStorage tokenStorage, ConverterService converterService, EncrypterService encrypterService) {
    return new UserServiceImpl(userStorage, tokenStorage, converterService, encrypterService);
  }

  @Provides
  @Singleton
  public EncrypterService providesEncrypterService(){
    return new JBCryptEncrypterService();
  }

}
