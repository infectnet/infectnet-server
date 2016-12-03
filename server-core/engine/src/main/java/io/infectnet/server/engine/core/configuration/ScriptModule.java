package io.infectnet.server.engine.core.configuration;

import groovy.lang.Binding;
import io.infectnet.server.engine.content.configuration.DslModule;
import io.infectnet.server.engine.content.configuration.SelectorModule;
import io.infectnet.server.engine.content.dsl.DslBindingCustomizer;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.code.CodeRepository;
import io.infectnet.server.engine.core.script.code.CodeRepositoryImpl;
import io.infectnet.server.engine.core.script.execution.BindingContext;
import io.infectnet.server.engine.core.script.execution.ScriptExecutor;
import io.infectnet.server.engine.core.script.execution.ScriptExecutorImpl;
import io.infectnet.server.engine.core.script.generation.ScriptGenerator;
import io.infectnet.server.engine.core.script.generation.ScriptGeneratorImpl;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;

@Module(includes = {DslModule.class, SelectorModule.class})
public class ScriptModule {
  @Provides
  @Singleton
  public static ScriptGenerator providesScriptGenerator(Set<CompilationCustomizer> customizers) {
    return ScriptGeneratorImpl.usingCustomizers(customizers);
  }

  @Provides
  @Singleton
  public static ScriptExecutor providesScriptExecutor(
      Function<Player, BindingContext> playerBindingContextFunction) {
    return new ScriptExecutorImpl(playerBindingContextFunction);
  }

  @Provides
  @Singleton
  public static CodeRepository providesCodeRepository() {
    return new CodeRepositoryImpl();
  }

  @Provides
  @ElementsIntoSet
  public static Set<CompilationCustomizer> providesDefaultEmptyCompilationCustomizerSet() {
    return Collections.emptySet();
  }

  @Provides
  @ElementsIntoSet
  public static Set<DslBindingCustomizer> providesDefaultEmptyDslBindingCustomizerSet() {
    return Collections.emptySet();
  }

  @Provides
  @Singleton
  public static Function<Player, BindingContext> providesBindingSupplier(
      Set<DslBindingCustomizer> customizerSet) {
    return (player) -> {
      BindingContext bindingContext = new BindingContext(player, new Binding());

      customizerSet.forEach(c -> c.customize(bindingContext));

      return bindingContext;
    };
  }
}
