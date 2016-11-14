package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.core.player.Player;

import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;

@Module(includes = {
    SelectorModule.class,
    DslModule.class,
    SystemModule.class,
    TypeModule.class,
    WrapperModule.class})
public class ContentModule {
  @Provides
  @Singleton
  public static Function<Player, Player> providesIdentityPlayerInitializer() {
    return Function.identity();
  }

  @Provides
  @ElementsIntoSet
  public static Set<CompilationCustomizer> providesEmptyCompilationCustomizerSet() {
    return Collections.emptySet();
  }
}
