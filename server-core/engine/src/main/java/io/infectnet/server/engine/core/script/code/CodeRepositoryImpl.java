package io.infectnet.server.engine.core.script.code;

import io.infectnet.server.engine.core.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CodeRepositoryImpl implements CodeRepository {

  private static final Logger logger = LoggerFactory.getLogger(CodeRepositoryImpl.class);

  private final Map<String, Code> codeMap;

  public CodeRepositoryImpl() {
    this.codeMap = new ConcurrentHashMap<>();
  }

  @Override
  public void addCode(Player player, Code code) {
    Objects.requireNonNull(player);

    codeMap.put(player.getUsername(), Objects.requireNonNull(code));

    logger.debug("New Code added for Player: {}", player);
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
