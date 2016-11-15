package io.infectnet.server.engine.core.script.code;

import groovy.lang.Script;

import io.infectnet.server.engine.core.player.Player;

import java.util.Optional;

/**
 * {@code Code} stores a runnable {@link Script} instance associated with its owner and source code.
 * A new {@code Code} instance is created even from erroneous source codes so that {@link Player}s
 * can retrieve their current source code.
 */
public class Code {
  private final Player owner;

  private final Script script;

  private final String source;

  /**
   * Constructs a new instance with {@code Script} set to {@code null}.
   * @param owner the {@code Player} this instance belongs to
   * @param source the source code
   */
  public Code(Player owner, String source) {
    this.owner = owner;

    this.script = null;

    this.source = source;
  }

  /**
   * Constructs a new instance using the specified parameters.
   * @param owner the {@code Player} this instance belongs to
   * @param source the source code
   * @param script the runnable {@code Script}
   */
  public Code(Player owner, String source, Script script) {
    this.owner = owner;

    this.script = script;

    this.source = source;
  }

  public Player getOwner() {
    return owner;
  }

  public Optional<Script> getScript() {
    return Optional.ofNullable(script);
  }

  public String getSource() {
    return source;
  }

  public boolean isRunnable() {
    return script != null;
  }
}
