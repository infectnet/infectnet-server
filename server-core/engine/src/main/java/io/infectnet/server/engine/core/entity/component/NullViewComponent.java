package io.infectnet.server.engine.core.entity.component;


public class NullViewComponent extends ViewComponent {

  private static NullViewComponent instance = new NullViewComponent();

  public static NullViewComponent getInstance() {
    return instance;
  }

  @Override
  public void setViewRadius(int viewRadius) {
    /*
     * Do nothing...
     */
  }
}
