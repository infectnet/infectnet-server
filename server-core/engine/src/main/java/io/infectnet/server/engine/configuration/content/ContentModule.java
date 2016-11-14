package io.infectnet.server.engine.configuration.content;

import io.infectnet.server.engine.player.Player;

import java.util.function.Function;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module(includes = {SelectorModule.class, DslModule.class, SystemModule.class})
public class ContentModule {
  @Provides
  @Singleton
  public static Function<Player, Player> providesIdentityPlayerInitializer() {
    return Function.identity();
  }
}
