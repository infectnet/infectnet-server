package io.infectnet.server.engine.core.script.selector;

import io.infectnet.server.engine.core.dsl.DslBindingCustomizer;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.execution.BindingContext;

/**
 * Factory base that can produce {@code Selector} instances. Factories must have an associated name
 * that will be exposed to the clients of the DSL. For example, if the associated name is
 * {@code own}, then the instance produced by the factory will be available in the DSL as
 * {@code own}.
 * @param <T> the produced {@code Selector}
 */
public abstract class SelectorFactory<T extends Selector> implements DslBindingCustomizer {
  protected final String name;

  /**
   * Constructs a new instance using the passed name.
   * @param name the name
   */
  public SelectorFactory(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Produces a new {@link T} instance with the specified {@code Player} passed to it.
   * @param player the {@code Player} whose code will use the produced {@code Selector}
   * @return a {@code Selector} ready to be used in the DSL
   */
  public abstract T forPlayer(Player player);

  @Override
  public void customize(BindingContext bindingContext) {
    bindingContext.getBinding().setVariable(getName(), forPlayer(bindingContext.getPlayer()));
  }
}
