package io.infectnet.server.engine;

import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapper;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperFactory;
import io.infectnet.server.engine.core.entity.wrapper.EntityWrapperRepository;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;

import java.util.Map;
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
  /* package */ Map<String, EntityWrapperFactory<? extends EntityWrapper>> wrapperFactoryMap;

  @Inject
  /* package */ Set<TypeComponent> typeComponents;

  @Inject
  /* package */ TypeRepository typeRepository;

  @Inject
  /* package */ EntityWrapperRepository entityWrapperRepository;

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

    typeComponents.forEach(typeRepository::registerType);

    wrapperFactoryMap.forEach((typeName, factory) -> {
      typeRepository.getTypeByName(typeName)
          .ifPresent(type -> entityWrapperRepository.registerFactoryForType(type, factory));
    });
  }
}