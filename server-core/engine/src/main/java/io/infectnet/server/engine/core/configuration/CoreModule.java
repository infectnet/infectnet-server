package io.infectnet.server.engine.core.configuration;

import io.infectnet.server.engine.core.GameLoop;
import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.script.code.CodeRepository;
import io.infectnet.server.engine.core.script.execution.ScriptExecutor;
import io.infectnet.server.engine.core.util.ListenableQueue;

import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ScriptModule.class, EntityModule.class, PlayerModule.class})
public class CoreModule {
  @Provides
  @Singleton
  @Named("Action Queue")
  public static ListenableQueue<Action> providesActionQueue() {
    return new ListenableQueue<>();
  }

  @Provides
  @Singleton
  @Named("Request Queue")
  public static ListenableQueue<Request> providesRequestQueue() {
    return new ListenableQueue<>();
  }

  @Provides
  @Singleton
  public static GameLoop providesGameLoop(
      @Named("Action Queue") ListenableQueue<Action> actionQueue,
      @Named("Request Queue") ListenableQueue<Request> requestQueue,
      CodeRepository codeRepository, ScriptExecutor scriptExecutor) {
    return new GameLoop(actionQueue, requestQueue, codeRepository, scriptExecutor);
  }
}
