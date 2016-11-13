package io.infectnet.server.engine.configuration.core;

import io.infectnet.server.engine.GameLoop;
import io.infectnet.server.engine.entity.wrapper.Action;
import io.infectnet.server.engine.script.Request;
import io.infectnet.server.engine.script.code.CodeRepository;
import io.infectnet.server.engine.script.execution.ScriptExecutor;
import io.infectnet.server.engine.util.ListenableQueue;

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
  public static ListenableQueue<Action> providesRequestQueue() {
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
