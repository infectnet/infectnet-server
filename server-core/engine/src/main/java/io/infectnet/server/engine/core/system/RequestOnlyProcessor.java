package io.infectnet.server.engine.core.system;

import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.util.ListenableQueue;

/**
 * Adapter class for Systems that only listen for {@link Request}s and ignore {@link Action}s.
 */
public abstract class RequestOnlyProcessor implements ProcessorSystem {
  @Override
  public final void registerActionListeners(ListenableQueue<Action> actionQueue) {
    /*
     * Do nothing.
     */
  }

  @Override
  public abstract void registerRequestListeners(ListenableQueue<Request> requestQueue);
}
