package io.infectnet.server.engine.script;

import io.infectnet.server.engine.script.generation.ScriptGenerator;
import io.infectnet.server.engine.script.generation.ScriptGeneratorImpl;
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
