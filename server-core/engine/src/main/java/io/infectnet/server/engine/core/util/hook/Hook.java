package io.infectnet.server.engine.core.util.hook;

import io.infectnet.server.engine.core.util.Ordered;

/**
 * Interface that represents a hook that can be called on hook points, specific points of the
 * execution flow. For example hooks can be called after some initialization or setup has
 * completed or before the application shuts down, etc.
 * <p>
 * Hooks must implement the {@code Ordered} interface to ensure that the order in which they are
 * executed is deterministic.
 * </p>
 */
public interface Hook extends Ordered {
  /**
   * The action this hook performs.
   */
  void execute();

  @Override
  int getOrder();

  /**
   * Constructs a new default implementation of {@code Hook} with the specified order and
   * the {@code Runnable's} {@link Runnable#run()} methds as the {@link #execute()} method implemen
   * tation.
   * @param order the order of the {@code Hook}
   * @param runnable the {@code Runnable} that represents the action
   * @return a new {@code Hook} instance
   */
  static Hook from(int order, Runnable runnable) {
    return new RunnableBackedHook(order, runnable);
  };

  final class RunnableBackedHook implements Hook {
    private final Runnable runnable;

    private final int order;

    private RunnableBackedHook(int order, Runnable runnable) {
      this.order = order;

      this.runnable = runnable;
    }

    @Override
    public void execute() {
      runnable.run();
    }

    @Override
    public int getOrder() {
      return order;
    }
  }
}
