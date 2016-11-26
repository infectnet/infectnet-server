package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.world.strategy.WorldGeneratorStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldImpl implements World {
  /**
   * The strategy used to generate the world.
   */
  private final WorldGeneratorStrategy strategy;
  /**
   * The two-dimensional array to hold all tiles in the {@link World}
   */
  private Tile[][] tiles;

  /**
   * The height of the world.
   */
  private int height;

  /**
   * The width of the world.
   */
  private int width;

  /**
   * The HashMap containing all Entities which are on the World,
   * with the Entity as the key, and a position as value.
   */
  private final HashMap<Entity, Tile> entityPositionMap;

  /**
   * Creates a new World in a size defined by the parameters. All its tiles are generated at random.
   * @param strategy the strategy to generate the world
   */
  public WorldImpl(WorldGeneratorStrategy strategy) {
    this.strategy = strategy;

    entityPositionMap = new HashMap<>();
  }

  @Override
  public List<Entity> seenBy(Entity entity) {

    int viewRadius = entity.getViewComponent().getViewRadius();
    Position position = entity.getPositionComponent().getPosition();

    return entitiesWithinRadius(viewRadius, position);
  }

  @Override
  public List<Entity> neighboursOf(Entity entity) {
    Position position = entity.getPositionComponent().getPosition();

    return entitiesWithinRadius(1, position);
  }

  /**
   * Returns a list containing all Entities that are visible for the {@link Entity} given.
   * @param radius the distance it searches in
   * @param position the center of the search
   * @return a list of the found Entities
   */
  private List<Entity> entitiesWithinRadius(int radius, Position position) {
    List<Entity> list = new ArrayList<>();

    ViewBox viewBox = new ViewBox(radius, position, this);

    for (int i = viewBox.northLimitHeight; i <= viewBox.southLimitHeight; ++i) {
      for (int j = viewBox.westLimitWidth; j <= viewBox.eastLimitWidth; ++j) {
        Entity en = tiles[i][j].getEntity();
        if (en != null && position.getH() != i && position.getW() != j) {
          list.add(en);
        }
      }
    }

    return list;
  }

  @Override
  public List<Tile> viewSight(Entity entity) {
    List<Tile> list = new ArrayList<>();
    int viewRadius = entity.getViewComponent().getViewRadius();
    Position position = entity.getPositionComponent().getPosition();

    ViewBox viewBox = new ViewBox(viewRadius, position, this);

    for (int i = viewBox.northLimitHeight; i <= viewBox.southLimitHeight; ++i) {
      for (int j = viewBox.westLimitWidth; j <= viewBox.eastLimitWidth; ++j) {
        Tile tile = tiles[i][j];
        if (position.getH() != i && position.getW() != j) {
          list.add(tile);
        }
      }
    }

    return list;
  }

  @Override
  public void generate(int height, int width) {
    tiles = new Tile[height][width];

    boolean[][] cells = strategy.generateWorld(height, width);

    this.height = height;
    this.width = width;

    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        if (isBorder(i, j)) {
          tiles[i][j] = new Tile(TileType.ROCK,  new Position(i, j));
        } else if (cells[i][j] == strategy.CAVE) {
          tiles[i][j] = new Tile(TileType.CAVE,  new Position(i, j));
        } else {
          tiles[i][j] = new Tile(TileType.ROCK,  new Position(i, j));
        }
      }
    }
  }

  /**
   * Checks if the given coordinates are on the edge of the World.
   * @param i the y coordinate
   * @param j the x coordinate
   * @return true if it is on the border, false otherwise
   */
  private boolean isBorder(int i, int j) {
    return i == 0 || i == tiles.length - 1
        || j == 0 || j == tiles[i].length - 1;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public HashMap<Entity, Tile> getEntityPositionMap() {
    return entityPositionMap;
  }

  public Tile getTileByPosition(Position position){
    return tiles[position.getH()][position.getW()];
  }

  public boolean isPositionValidTile(Position position){
    return position.getH() >= 0 && position.getH() < height && position.getW() >= 0 && position.getW() < width;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
  /**
   *An inner class to represent the limitations of each Entity's view sight.
   */
  private static class ViewBox {

    private final int northLimitHeight;
    private final int southLimitHeight;
    private final int westLimitWidth;
    private final int eastLimitWidth;

    ViewBox(int viewRadius, Position position, WorldImpl world) {
      northLimitHeight = Math.max(0, position.getH() - viewRadius);
      southLimitHeight = Math.min(world.height - 1, position.getH() + viewRadius);
      westLimitWidth = Math.max(0, position.getW() - viewRadius);
      eastLimitWidth = Math.min(world.width - 1, position.getW() + viewRadius);
    }
  }
}
