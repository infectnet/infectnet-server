package io.infectnet.server.engine.core.script.generation;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

/**
 * Script generator that uses the {@link GroovyShell} to parse the source code and emit
 * {@link Script} objects.
 */
public class ScriptGeneratorImpl implements ScriptGenerator {

  private static final Logger logger = LoggerFactory.getLogger(ScriptGeneratorImpl.class);

  private final GroovyShell groovyShell;

  /**
   * Creates a new instance that uses the specified compilation customizers.
   * @param customizers the set of customizers to be used by the generator instance
   * @return a new generator instance
   */
  public static ScriptGeneratorImpl usingCustomizers(Set<CompilationCustomizer> customizers) {
    return new ScriptGeneratorImpl(Objects.requireNonNull(customizers));
  }

  private ScriptGeneratorImpl(Set<CompilationCustomizer> customizers) {
    this.groovyShell = new GroovyShell(compilerConfiguration(customizers));
  }

  @Override
  public Script generateFromCode(String sourceCode) throws ScriptGenerationFailedException {
    try {
      logger.debug("Compiling new source: {} ...", StringUtils.left(sourceCode, 15));

      return groovyShell.parse(Objects.requireNonNull(sourceCode));
    } catch (CompilationFailedException e) {
      logger.debug("Syntax error found during Script generation: {}", e.getMessage());

      throw new ScriptGenerationFailedException(e);
    }
  }

  private CompilerConfiguration compilerConfiguration(Set<CompilationCustomizer> customizers) {
    CompilerConfiguration config = new CompilerConfiguration();

    customizers.forEach(config::addCompilationCustomizers);

    return config;
  }
}
