package io.infectnet.server.engine.script.execution;

import groovy.lang.Script;
import io.infectnet.server.engine.player.Player;

public interface ScriptExecutor {
  void execute(Script script, Player owner);
}
