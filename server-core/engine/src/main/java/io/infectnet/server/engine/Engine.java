package io.infectnet.server.engine;

import groovy.lang.Script;
import io.infectnet.server.engine.content.type.WorkerTypeComponent;
import io.infectnet.server.engine.core.GameLoop;
import io.infectnet.server.engine.core.configuration.CoreModule;
import io.infectnet.server.engine.content.configuration.ContentModule;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.EntityManager;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.script.code.Code;
import io.infectnet.server.engine.core.script.code.CodeRepository;
import io.infectnet.server.engine.core.script.generation.ScriptGenerationFailedException;
import io.infectnet.server.engine.core.script.generation.ScriptGenerator;

import java.time.Duration;
import java.util.Optional;
import javax.inject.Singleton;
import dagger.Component;

public class Engine {

  @Singleton
  @Component(modules = { CoreModule.class, ContentModule.class })
  interface Bootstrapper {
    EngineConfigurator getEngineConfigurator();

    GameLoop getGameLoop();

    PlayerService getPlayerService();

    CodeRepository getCodeRepository();

    ScriptGenerator getScriptGenerator();

    EntityManager getEntityManager();

    TypeRepository getTypeRepository();
  }

  private final Bootstrapper bootstrapper;

  public static Engine create() {
    return new Engine();
  }

  /**
   * Cannot be instantiated directly.
   */
  private Engine() {
    this.bootstrapper = DaggerEngine_Bootstrapper.create();

    bootstrapper.getEngineConfigurator().configure();

    bootstrapper.getGameLoop().setDesiredTickDuration(Duration.ofSeconds(5L));
  }

  public Optional<Player> registerPlayer(String username) {
    return bootstrapper.getPlayerService().createPlayer(username);
  }

  public void addCode(Player player, String sourceCode) throws ScriptGenerationFailedException {
    Script script = bootstrapper.getScriptGenerator().generateFromCode(sourceCode);

    bootstrapper.getCodeRepository().addCode(player, new Code(player, sourceCode, script));
  }

  public void start() {
    bootstrapper.getGameLoop().start();
  }

  public void addEntityTo(Player player) {
    Optional<TypeComponent> type =
        bootstrapper.getTypeRepository().getTypeByName(WorkerTypeComponent.TYPE_NAME);

    Entity worker = type.get().createEntityOfType();

    worker.getOwnerComponent().setOwner(player);

    bootstrapper.getEntityManager().addEntity(worker);
  }
}
