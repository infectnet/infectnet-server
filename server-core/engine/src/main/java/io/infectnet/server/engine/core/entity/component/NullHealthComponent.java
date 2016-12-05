package io.infectnet.server.engine.core.entity.component;

public class NullHealthComponent extends HealthComponent {

  private static NullHealthComponent instance = new NullHealthComponent();

  public static NullHealthComponent getInstance() {
    return instance;
  }

}
