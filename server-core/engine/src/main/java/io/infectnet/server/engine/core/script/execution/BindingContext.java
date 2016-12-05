package io.infectnet.server.engine.core.script.execution;

import groovy.lang.Binding;
import io.infectnet.server.engine.core.player.Player;


public class BindingContext {

  private final Player player;

  private final Binding binding;

  public BindingContext(Player player, Binding binding) {
    this.player = player;
    this.binding = binding;
  }

  public Player getPlayer() {
    return player;
  }

  public Binding getBinding() {
    return binding;
  }
}
