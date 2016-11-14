package io.infectnet.server.engine;

import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;

import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;

class EngineConfigurator {
  @Inject
  @Named("Action Queue")
  /* package */ ListenableQueue<Action> actionQueue;

  @Inject
  @Named("Request Queue")
  /* package */ ListenableQueue<Request> requestQueue;

  @Inject
  /* package */ Set<ProcessorSystem> processorSystems;

  @Inject
  public EngineConfigurator() {
    /*
     * Only needed to let Dagger instantiate the class.
     */
  }

  public void configure() {
    processorSystems.forEach(s -> {
      s.registerActionListeners(actionQueue);

      s.registerRequestListeners(requestQueue);
    });
  }
}
