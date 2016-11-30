package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.system.hijack.HijackSystem;
import io.infectnet.server.engine.content.system.infect.InfectSystem;
import io.infectnet.server.engine.content.system.inventory.InventoryManagementSystem;
import io.infectnet.server.engine.content.system.kill.KillSystem;
import io.infectnet.server.engine.content.system.movement.MovementSystem;
import io.infectnet.server.engine.content.system.spawn.SpawnSystem;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.world.World;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SystemModule {

  @Provides
  @IntoSet
  public static ProcessorSystem providesInfectSystem(
      @Named("Request Queue") ListenableQueue<Request> requestQueue, World world) {
    return new InfectSystem(requestQueue, world);
  }

  @Provides
  @IntoSet
  public static ProcessorSystem providesInventoryManagementSystem() {
    return new InventoryManagementSystem();
  }

  @Provides
  @IntoSet
  public static ProcessorSystem providesMovementSystem(
      @Named("Request Queue") ListenableQueue<Request> requestQueue, World world) {
    return new MovementSystem(requestQueue, world);
  }

  @Provides
  @IntoSet
  public static ProcessorSystem providesHijackSystem(
      @Named("Request Queue") ListenableQueue<Request> requestQueue) {
    return new HijackSystem(requestQueue);
  }

  @Provides
  @IntoSet
  public static ProcessorSystem providesKillSystem(EntityManager entityManager, World world) {
    return new KillSystem(entityManager, world);
  }

  @Provides
  @IntoSet
  public static ProcessorSystem providesSpawnSystem(
      @Named("Request Queue") ListenableQueue<Request> requestQueue,
      TypeRepository typeRepository,
      EntityManager entityManager,
      World world) {
    return new SpawnSystem(requestQueue, typeRepository, entityManager, world);
  }
}
