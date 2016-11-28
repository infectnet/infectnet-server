package io.infectnet.server.engine.content.system.infect;


import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ActionOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.World;

public class InfectSystem extends ActionOnlyProcessor {

  private final ListenableQueue<Request> requestQueue;

  private final World world;

  public InfectSystem(ListenableQueue<Request> requestQueue, World world) {
    this.requestQueue = requestQueue;
    this.world = world;
  }

  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    actionQueue.addListener(InfectAction.class, this::consumeInfectAction);
  }

  private void consumeInfectAction(Action action) {
    InfectAction infectAction = (InfectAction) action;

    if (world.neighboursOf(infectAction.getSource()).contains(infectAction.getResource())) {
      //TODO requests
    }

  }

}
