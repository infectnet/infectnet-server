package io.infectnet.server.engine.script.selector;

import io.infectnet.server.engine.player.Player;

/**
 * Base class for expressions that can be used in SFA blocks
 * (see {@link io.infectnet.server.engine.script.dsl.SelectFilterActionBlock}). Please note that all
 * public methods will be available in the Groovy DSL. The {@link Player} passed to an instance is
 * the player whose code uses the instance.
 * <p>
 * Descendants are free to provide any number of methods that return a {@link java.util.Collection}.
 * These methods will be the actual selectors.
 * </p>
 * <p>
 * If a selector method does not take arguments then it must be implemented as a getter, so instead
 * of a method named {@code workers()}, the method {@code getWorkers()} should be declared. However
 * when a selector method does take arguments this restriction does not apply.
 * </p>
 * <p>
 * Please provide both singular and plural named versions of the same selector. This means that both
 * {@code getWorker()} and {@code getWorkers()} should be declared with the same functionality.
 * </p>
 * <p>
 * Groovy enables users to use named parameters, therefore please expose descriptive parameter
 * names.
 * </p>
 */
public abstract class Selector {
  protected final Player player;

  /**
   * Constructs a new instance with the specified {@code Player}.
   * @param player the {@code Player} whose code uses the instance
   */
  Selector(Player player) {
    this.player = player;
  }
}
