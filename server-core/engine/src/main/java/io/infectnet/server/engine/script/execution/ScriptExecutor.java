package io.infectnet.server.engine.script.execution;

import groovy.lang.Script;
import io.infectnet.server.engine.player.Player;

/**
 * Interface for classes that can execute player-written {@link Script}s.
 */
public interface ScriptExecutor {
  /**
   * Executes the specified {@code Script}.
   * @param script the {@code Script} to be executed
   * @param owner the {@code Player} who owns the {@code Script}
   */
  void execute(Script script, Player owner);
}
