package io.infectnet.server.engine.core.util;

/**
 * Interface that represents a hook that can be called on hook points, specific points of the
 * execution flow. For example hooks can be called after some initialization or setup has
 * completed or before the application shuts down, etc.
 */
@FunctionalInterface
public interface Hook {
  /**
   * The action this hook performs.
   */
  void execute();
}
