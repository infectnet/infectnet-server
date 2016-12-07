package io.infectnet.server.engine;

import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.hook.Hook;
import io.infectnet.server.engine.core.util.ListenableQueue;
import io.infectnet.server.engine.core.util.OrderedList;
import io.infectnet.server.engine.core.util.hook.PostSetupHook;
import io.infectnet.server.engine.core.world.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;

class EngineConfigurator {
  private static final Logger logger = LoggerFactory.getLogger(EngineConfigurator.class);

  @Inject
  @Named("Action Queue")
  /* package */ ListenableQueue<Action> actionQueue;

  @Inject
  @Named("Request Queue")
  /* package */ ListenableQueue<Request> requestQueue;

  @Inject
  /* package */ Set<ProcessorSystem> processorSystems;

  @Inject
  /* package */ Map<String, EntityWrapperFactory<? extends EntityWrapper>> wrapperFactoryMap;

  @Inject
  /* package */ Set<TypeComponent> typeComponents;

  @Inject
  /* package */ TypeRepository typeRepository;

  @Inject
  /* package */ EntityWrapperRepository entityWrapperRepository;

  @Inject
  /* package */ World world;

  @Inject
  @PostSetupHook
  /* package */ Set<Hook> postSetupHooks;

  @Inject
  public EngineConfigurator() {
    /*
     * Only needed to let Dagger instantiate the class.
     */
  }

  public void configure() {
    logger.info("Starting engine configuration...");

    logger.info("Registering {} Systems...", processorSystems.size());

    processorSystems.forEach(s -> {
      s.registerActionListeners(actionQueue);

      s.registerRequestListeners(requestQueue);
    });

    logger.info("Registering {} TypeComponents...", typeComponents.size());

    typeComponents.forEach(typeRepository::registerType);

    logger.info("Registering {} type-to-wrapper mappings...", wrapperFactoryMap.size());

    wrapperFactoryMap.forEach((typeName, factory) -> {
      typeRepository.getTypeByName(typeName)
          .ifPresent(type -> entityWrapperRepository.registerFactoryForType(type, factory));
    });

    world.generate(1000, 1000);

    logger.info("Running {} post setup hooks...", postSetupHooks.size());

    OrderedList.of(postSetupHooks).getBackingList().forEach(Hook::execute);
  }
}
