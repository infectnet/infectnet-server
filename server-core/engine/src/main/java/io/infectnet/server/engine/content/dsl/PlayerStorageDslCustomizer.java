package io.infectnet.server.engine.content.dsl;

import io.infectnet.server.engine.core.player.storage.PlayerStorage;
import io.infectnet.server.engine.core.player.storage.PlayerStorageService;
import io.infectnet.server.engine.core.script.execution.BindingContext;

public class PlayerStorageDslCustomizer implements DslBindingCustomizer {

  private static final String SELF_KEYWORD = "self";
  private static final String MEMORY_KEYWORD = "memory";

  private final PlayerStorageService playerStorageService;

  public PlayerStorageDslCustomizer(PlayerStorageService playerStorageService) {
    this.playerStorageService = playerStorageService;
  }

  @Override
  public void customize(BindingContext bindingContext) {
    playerStorageService.getStorageForPlayer(bindingContext.getPlayer())
        .ifPresent(playerStorage -> {
          bindingContext.getBinding()
              .setVariable(SELF_KEYWORD, new SelfStorageWrapper(playerStorage));

          bindingContext.getBinding()
              .setVariable(MEMORY_KEYWORD, new MemoryStorageWrapper(playerStorage));
        });
  }

  private static class SelfStorageWrapper {
    private final PlayerStorage playerStorage;

    private SelfStorageWrapper(PlayerStorage playerStorage) {
      this.playerStorage = playerStorage;
    }

    public Object getAt(String key) {
      return playerStorage.getAttribute(key).orElse(null);
    }

    public boolean isCase(String key) {
      return playerStorage.getAttribute(key).isPresent();
    }
  }

  private static class MemoryStorageWrapper {
    private final PlayerStorage playerStorage;

    private MemoryStorageWrapper(PlayerStorage playerStorage) {
      this.playerStorage = playerStorage;
    }

    public Object getAt(String key) {
      return playerStorage.getRecord(key).orElse(null);
    }

    public void putAt(String key, Object value) {
      playerStorage.setRecord(key, value);
    }

    public void remove(String key) {
      playerStorage.removeRecord(key);
    }

    public boolean isCase(String key) {
      return playerStorage.getRecord(key).isPresent();
    }
  }


}
