package io.infectnet.server.engine.script.code;

import io.infectnet.server.engine.player.Player;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CodeRepositoryImpl implements CodeRepository {
  private final Map<String, Code> codeMap;

  public CodeRepositoryImpl() {
    this.codeMap = new ConcurrentHashMap<>();
  }

  @Override
  public void addCode(Player player, Code code) {
    Objects.requireNonNull(player);

    codeMap.put(player.getUsername(), Objects.requireNonNull(code));
  }

  @Override
  public Optional<Code> getCodeByPlayer(Player player) {
    return Optional.ofNullable(codeMap.get(Objects.requireNonNull(player.getUsername())));
  }

  @Override
  public Collection<Code> getAllCodes() {
    return codeMap.values();
  }
}
