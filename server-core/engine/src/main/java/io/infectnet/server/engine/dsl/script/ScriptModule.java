package io.infectnet.server.engine.dsl.script;

import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import java.util.Set;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ScriptModule {
  @Provides
  @Singleton
  public static ScriptGenerator providesScriptGenerator(Set<CompilationCustomizer> customizers) {
    return ScriptGeneratorImpl.usingCustomizers(customizers);
  }
}
