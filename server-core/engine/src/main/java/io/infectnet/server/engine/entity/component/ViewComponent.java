package io.infectnet.server.engine.entity.component;

/**
 * {@code ViewComponent} adds view radius to an {@link io.infectnet.server.engine.entity.Entity}.
 * The view radius determines whether an {@code Entity} see a tile or not.
 */
public class ViewComponent {
  private static final int DEFAULT_VIEW_RADIUS = 0;

  private int viewRadius;

  public ViewComponent() {
    this(DEFAULT_VIEW_RADIUS);
  }

  public ViewComponent(int viewRadius) {
    this.viewRadius = viewRadius;
  }

  public int getViewRadius() {
    return viewRadius;
  }

  public void setViewRadius(int viewRadius) {
    this.viewRadius = viewRadius;
  }
}
