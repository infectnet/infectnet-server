package io.infectnet.server.engine.core.script.generation;

import groovy.lang.Script;

/**
 * Interface for classes that can generate executable {@link Script} from Groovy source code.
 */
public interface ScriptGenerator {
  /**
   * Generates {@code Script} from the passed source code. The returned instance can be stored
   * and executed later.
   * @param sourceCode the source code to parse
   * @return an executable {@code Script} instance
   * @throws ScriptGenerationFailedException if the generation process fails
   */
  Script generateFromCode(String sourceCode) throws ScriptGenerationFailedException;
}
