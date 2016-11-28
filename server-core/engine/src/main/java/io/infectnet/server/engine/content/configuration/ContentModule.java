package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.core.player.Player;

import java.util.function.Function;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module(includes = {SelectorModule.class, DslModule.class, SystemModule.class, TypeModule.class})
public class ContentModule {
  @Provides
  @Singleton
  public static Function<Player, Player> providesIdentityPlayerInitializer() {
    return Function.identity();
  }
}
