package io.infectnet.server.engine.system;

import io.infectnet.server.engine.entity.wrapper.Action;
import io.infectnet.server.engine.script.Request;
import io.infectnet.server.engine.util.ListenableQueue;

/**
 * Interface for Systems that can process {@link Action}s and {@link Request}.
 */
public interface ProcessorSystem {
  /**
   * Registers the action listeners provided by this System on the specified action queue.
   * @param actionQueue the queue to register the listeners on
   */
  void registerActionListeners(ListenableQueue<Action> actionQueue);

  /**
   * Registers the request listeners provided by this System on the specified request queue.
   * @param requestQueue the queue to register the listeners on
   */
  void registerRequestListeners(ListenableQueue<Request> requestQueue);
}
