package io.infectnet.server.engine.core.configuration;

import groovy.lang.Binding;
import io.infectnet.server.engine.content.configuration.DslModule;
import io.infectnet.server.engine.content.configuration.SelectorModule;
import io.infectnet.server.engine.core.script.code.CodeRepository;
import io.infectnet.server.engine.core.script.code.CodeRepositoryImpl;
import io.infectnet.server.engine.core.script.dsl.DslBindingCustomizer;
import io.infectnet.server.engine.core.script.execution.ScriptExecutor;
import io.infectnet.server.engine.core.script.execution.ScriptExecutorImpl;
import io.infectnet.server.engine.core.script.generation.ScriptGenerator;
import io.infectnet.server.engine.core.script.generation.ScriptGeneratorImpl;
import io.infectnet.server.engine.core.script.selector.Selector;
import io.infectnet.server.engine.core.script.selector.SelectorFactory;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import java.util.Set;
import java.util.function.Supplier;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module(includes = { DslModule.class, SelectorModule.class })
public class ScriptModule {
  @Provides
  @Singleton
  public static ScriptGenerator providesScriptGenerator(Set<CompilationCustomizer> customizers) {
    return ScriptGeneratorImpl.usingCustomizers(customizers);
  }

  @Provides
  @Singleton
  public static ScriptExecutor providesScriptExecutor(
      Set<SelectorFactory<? extends Selector>> selectorFactories,
      Supplier<Binding> bindingSupplier) {
    return new ScriptExecutorImpl(selectorFactories, bindingSupplier);
  }

  @Provides
  @Singleton
  public static CodeRepository providesCodeRepository() {
    return new CodeRepositoryImpl();
  }

  @Provides
  @Singleton
  public static Supplier<Binding> providesBindingSupplier(Set<DslBindingCustomizer> customizerSet) {
    return () -> {
      Binding binding = new Binding();

      customizerSet.forEach(c -> c.customize(binding));

      return binding;
    };
  }
}
