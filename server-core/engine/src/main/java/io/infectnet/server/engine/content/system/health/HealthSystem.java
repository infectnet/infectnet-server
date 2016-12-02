package io.infectnet.server.engine.content.system.health;


import io.infectnet.server.engine.content.system.kill.KillRequest;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.RequestOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;

public class HealthSystem extends RequestOnlyProcessor {

  private final ListenableQueue<Request> requestQueue;

  public HealthSystem(ListenableQueue<Request> requestQueue) {
    this.requestQueue = requestQueue;
  }

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue
        .addListener(HealthModificationRequest.class, this::consumeHealthModificationRequest);
  }

  private void consumeHealthModificationRequest(Request request) {
    HealthModificationRequest healthModificationRequest = (HealthModificationRequest) request;

    // This Optional.get() is safe to do, because HealthModificationRequests always have targets.
    // This is enforced by the constructor.
    Entity modificationTarget = healthModificationRequest.getTarget().get();

    int modificationNumber = healthModificationRequest.getModificationNumber();

    modificationTarget.getHealthComponent()
        .setHealth(modificationTarget.getHealthComponent().getHealth() + modificationNumber);

    if (modificationTarget.getHealthComponent().getHealth() <= 0) {
      requestQueue
          .add(new KillRequest(modificationTarget, healthModificationRequest.getOrigin().get()));
    }
  }

}
