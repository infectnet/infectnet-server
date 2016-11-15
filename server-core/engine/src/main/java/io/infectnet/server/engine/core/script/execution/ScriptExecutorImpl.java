package io.infectnet.server.engine.core.script.execution;

import groovy.lang.Binding;
import groovy.lang.Script;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.selector.Selector;
import io.infectnet.server.engine.core.script.selector.SelectorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ScriptExecutorImpl implements ScriptExecutor {
  private final Set<SelectorFactory<? extends Selector>> selectorFactories;

  private final Map<String, Binding> bindingMap;

  private final Supplier<Binding> bindingSupplier;

  public ScriptExecutorImpl(Set<SelectorFactory<? extends Selector>> selectorFactories,
                            Supplier<Binding> bindingSupplier) {
    this.selectorFactories = selectorFactories;

    this.bindingSupplier = bindingSupplier;

    this.bindingMap = new HashMap<>();
  }

  @Override
  public void execute(Script script, Player owner) {
    Objects.requireNonNull(script);

    Binding binding = createOrGetBinding(Objects.requireNonNull(owner));

    script.setBinding(binding);

    script.run();

    /*
     * Reset the binding.
     */
    script.setBinding(null);
  }

  private Binding createOrGetBinding(Player player) {
    Binding binding = bindingMap.get(player.getUsername());

    return binding != null ? binding : createBinding(player);
  }

  private Binding createBinding(Player player) {
    Binding binding = bindingSupplier.get();

    for (SelectorFactory<? extends Selector> factory : selectorFactories) {
      binding.setVariable(factory.getName(), factory.forPlayer(player));
    }

    bindingMap.put(player.getUsername(), binding);

    return binding;
  }
}
