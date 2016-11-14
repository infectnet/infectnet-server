package io.infectnet.server.engine.core.system;

import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.util.ListenableQueue;

/**
 * Adapter class for Systems that only listen for {@link Action}s and ignore {@link Request}s.
 */
public abstract class ActionOnlyProcessor implements ProcessorSystem {
  @Override
  public abstract void registerActionListeners(ListenableQueue<Action> actionQueue);

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    /*
     * Do nothing.
     */
  }
}
