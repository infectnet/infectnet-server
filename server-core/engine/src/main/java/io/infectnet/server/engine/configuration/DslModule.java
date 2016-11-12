package io.infectnet.server.engine.configuration;

import groovy.lang.Binding;
import io.infectnet.server.engine.script.dsl.CollectBlock;
import io.infectnet.server.engine.script.dsl.DslBindingCustomizer;
import io.infectnet.server.engine.script.dsl.SelectFilterActionBlock;

import java.util.Set;
import java.util.function.Supplier;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class DslModule {
  @Provides
  @Singleton
  @IntoSet
  public static DslBindingCustomizer providesCollectBlock() {
    return new CollectBlock();
  }

  @Provides
  @Singleton
  @IntoSet
  public static DslBindingCustomizer providesSelectFilterActionBlock() {
    return new SelectFilterActionBlock();
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
