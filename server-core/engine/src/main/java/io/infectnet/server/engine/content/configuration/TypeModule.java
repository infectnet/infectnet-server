package io.infectnet.server.engine.content.configuration;

import io.infectnet.server.engine.content.type.WorkerTypeComponent;
import io.infectnet.server.engine.core.entity.component.TypeComponent;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class TypeModule {
  @Provides
  @IntoSet
  public static TypeComponent providesWorkerTypeComponent() {
    return new WorkerTypeComponent();
  }
}
