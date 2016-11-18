package io.infectnet.server.engine;

import groovy.lang.Script;
import io.infectnet.server.engine.content.configuration.ContentModule;
import io.infectnet.server.engine.core.GameLoop;
import io.infectnet.server.engine.core.configuration.CoreModule;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.script.code.Code;
import io.infectnet.server.engine.core.script.code.CodeRepository;
import io.infectnet.server.engine.core.script.generation.ScriptGenerationFailedException;
import io.infectnet.server.engine.core.script.generation.ScriptGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import javax.inject.Singleton;
import dagger.Component;

public class Engine {

  private static final Logger logger = LoggerFactory.getLogger(Engine.class);

  private final Bootstrapper bootstrapper;

  @Singleton
  @Component(modules = {CoreModule.class, ContentModule.class})
  interface Bootstrapper {

    EngineConfigurator getEngineConfigurator();

    GameLoop getGameLoop();

    CodeRepository getCodeRepository();

    ScriptGenerator getScriptGenerator();

    PlayerService getPlayerService();

  }

  public static Engine create() {
    return new Engine();
  }

  /**
   * Cannot be instantiated directly.
   */
  private Engine() {
    this.bootstrapper = DaggerEngine_Bootstrapper.create();

    EngineConfigurator configurator = DaggerEngine_Bootstrapper.create().getEngineConfigurator();

    configurator.configure();
  }

  /**
   * Starts the game engine.
   */
  public void start(long desiredTickDuration) {
    bootstrapper.getGameLoop().start(desiredTickDuration);
  }

  /**
   * Stops the game engine and wait until it is done.
   * @return true if the engine stops, false otherwise
   */
  public boolean stopBlocking() {
    return bootstrapper.getGameLoop().stopAndWait();
  }

  /**
   * Stops the game engine asynchronously.
   * @return always true
   */
  public boolean stopAsync() {
    bootstrapper.getGameLoop().stop();
    return true;
  }

  /**
   * Compiles and stores the compiled script under the name of the player.
   * The source will be stored even if it cannot be compiled.
   * @param player the player who uploaded the source
   * @param source the source to be compiled
   * @throws ScriptGenerationFailedException thrown when there is syntax error in the code
   */
  public void compileAndUploadForPlayer(Player player, String source)
      throws ScriptGenerationFailedException {
    try {
      Script script = bootstrapper.getScriptGenerator().generateFromCode(source);

      bootstrapper.getCodeRepository().addCode(player, new Code(player, source, script));

    } catch (ScriptGenerationFailedException e) {
      bootstrapper.getCodeRepository().addCode(player, new Code(player, source));
      throw e;
    }
  }

  /**
   * Returns a {@link Player} with the specified player name and if necessary creates it.
   * @param playerName the player's name
   * @return the player
   */
  public Player createOrGetPlayer(String playerName) {

    Optional<Player> player = bootstrapper.getPlayerService().getPlayerByUsername(playerName);

    if (!player.isPresent()) {

      player = bootstrapper.getPlayerService().createPlayer(playerName);

      if (!player.isPresent()) {
        logger.warn("Player cannot be created with name: {}", playerName);

        throw new IllegalArgumentException("Player cannot be created with name: " + playerName);
      }

    }

    return player.get();
  }
}
