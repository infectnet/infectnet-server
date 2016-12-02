package io.infectnet.server.engine.content.system.kill;


import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.RequestOnlyProcessor;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.World;

public class KillSystem extends RequestOnlyProcessor {

  private final EntityManager entityManager;

  private final World world;

  public KillSystem(EntityManager entityManager, World world) {
    this.entityManager = entityManager;
    this.world = world;
  }

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue.addListener(KillRequest.class, this::consumeKillRequest);
  }

  private void consumeKillRequest(Request request) {
    KillRequest killRequest = (KillRequest) request;

    // This Optional.get() is safe to do, because KillRequests always have targets.
    // This is enforced by the constructor.
    Entity killTarget = killRequest.getTarget().get();

    entityManager.removeEntity(killTarget);

    world.setEntityOnPosition(null, killTarget.getPositionComponent().getPosition());

  }
}
