package io.infectnet.server.controller.configuration;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.token.TokenController;
import io.infectnet.server.service.configuration.ServiceModule;
import io.infectnet.server.service.token.TokenService;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module(includes = ServiceModule.class)
public class ControllerModule {

  @Provides
  @IntoSet
  public static RestController provideTokenController(TokenService tokenService) {
    TokenController tokenController = new TokenController();
    tokenController.setTokenService(tokenService);
    return tokenController;
  }


}
