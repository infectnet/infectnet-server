package io.infectnet.server.engine.system;

import io.infectnet.server.engine.entity.wrapper.Action;
import io.infectnet.server.engine.script.Request;
import io.infectnet.server.engine.util.ListenableQueue;

/**
 * Adapter class for Systems that only listen for {@link Request}s and ignore {@link Action}s.
 */
public abstract class RequestOnlyProcessor implements ProcessorSystem {
  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    /*
     * Do nothing.
     */
  }

  @Override
  public abstract void registerRequestListeners(ListenableQueue<Request> requestQueue);
}
