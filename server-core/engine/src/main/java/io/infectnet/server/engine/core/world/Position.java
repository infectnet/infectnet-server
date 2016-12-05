package io.infectnet.server.engine.core.world;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to hold two coordinates together, which define an exact {@link Tile} in the {@link
 * World}.
 */
public class Position {
  /**
   * The first component, referring to height.
   */
  private final int h;

  /**
   * The second component, referring to width.
   */
  private final int w;

  /**
   * Creates a {@code Position} with the given coordinates.
   * @param h the first component
   * @param w the second component
   */
  public Position(int h, int w) {
    this.h = h;
    this.w = w;
  }

  /**
   * Creates this Position's neighbour, stepping one step south.
   * @return the southern neighbour
   */
  public Position stepSouth() {
    return new Position(h + 1, w);
  }

  /**
   * Creates this Position's neighbour, stepping one step west.
   * @return the western neighbour
   */
  public Position stepWest() {
    return new Position(h, w - 1);
  }

  /**
   * Creates this Position's neighbour, stepping one step north.
   * @return the northern neighbour
   */
  public Position stepNorth() {
    return new Position(h - 1, w);
  }

  /**
   * Creates this Position's neighbour, stepping one step east.
   * @return the eastern neighbour
   */
  public Position stepEast() {
    return new Position(h, w + 1);
  }

  /**
   * Creates this Position's neighbour, stepping one step east and one step north.
   * @return the northeastern neighbour
   */
  public Position stepNorthEast() {
    return new Position(h - 1, w + 1);
  }

  /**
   * Creates this Position's neighbour, stepping one step west and one step north.
   * @return the northwestern neighbour
   */
  public Position stepNorthWest() {
    return new Position(h - 1, w - 1);
  }

  /**
   * Creates this Position's neighbour, stepping one step east and one step south.
   * @return the southeastern neighbour
   */
  public Position stepSouthEast() {
    return new Position(h + 1, w + 1);
  }

  /**
   * Creates this Position's neighbour, stepping one step west and one step south.
   * @return the southwestern neighbour
   */
  public Position stepSouthWest() {
    return new Position(h + 1, w - 1);
  }

  /**
   * Finds all neighbour Positions of the current Position.
   * All eight directions will be listed.
   * @return a list containing the neighbours
   */
  public List<Position> getNeighbours() {
    List<Position> neighbours = new ArrayList<>();

    neighbours.add(this.stepNorth());
    neighbours.add(this.stepNorthEast());
    neighbours.add(this.stepEast());
    neighbours.add(this.stepSouthEast());
    neighbours.add(this.stepSouth());
    neighbours.add(this.stepSouthWest());
    neighbours.add(this.stepWest());
    neighbours.add(this.stepNorthWest());

    return neighbours;
  }

  public int getH() {
    return h;
  }

  public int getW() {
    return w;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Position position = (Position) o;

    if (h != position.h) {
      return false;
    }
    return w == position.w;

  }

  @Override
  public int hashCode() {
    int result = h;
    result = 31 * result + w;
    return result;
  }
}
