package io.infectnet.server.engine.core.script.execution;

import groovy.lang.Script;
import io.infectnet.server.engine.core.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ScriptExecutorImpl implements ScriptExecutor {
  private final Map<String, BindingContext> bindingMap;

  private final Function<Player, BindingContext> playerBindingContextFunction;

  public ScriptExecutorImpl(Function<Player, BindingContext> playerBindingContextFunction) {
    this.playerBindingContextFunction = playerBindingContextFunction;

    this.bindingMap = new HashMap<>();
  }

  @Override
  public void execute(Script script, Player owner) {
    Objects.requireNonNull(script);

    BindingContext bindingContext = createOrGetBinding(Objects.requireNonNull(owner));

    script.setBinding(bindingContext.getBinding());

    script.run();

    /*
     * Reset the binding.
     */
    script.setBinding(null);
  }

  private BindingContext createOrGetBinding(Player player) {
    BindingContext bindingContext = bindingMap.get(player.getUsername());

    return bindingContext != null ? bindingContext : createBinding(player);
  }

  private BindingContext createBinding(Player player) {
    BindingContext bindingContext = playerBindingContextFunction.apply(player);

    bindingMap.put(player.getUsername(), bindingContext);

    return bindingContext;
  }
}
