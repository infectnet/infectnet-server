package io.infectnet.server.engine.content.system.hijack;


import io.infectnet.server.engine.content.system.health.HealthModificationRequest;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ActionOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;

public class HijackSystem extends ActionOnlyProcessor {

  private static final int MODIFICATION_NUMBER = 3;

  private final ListenableQueue<Request> requestQueue;

  public HijackSystem(
      ListenableQueue<Request> requestQueue) {
    this.requestQueue = requestQueue;
  }

  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    actionQueue.addListener(HijackAction.class, this::consumeHijackAction);
  }

  private void consumeHijackAction(Action action) {
    HijackAction hijackAction = (HijackAction) action;

    requestQueue.add(new HealthModificationRequest(hijackAction.getTargetEntity(), hijackAction,
        MODIFICATION_NUMBER));
  }
}
