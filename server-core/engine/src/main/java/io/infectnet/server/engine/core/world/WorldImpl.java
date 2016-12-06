package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.world.strategy.generation.WorldGeneratorStrategy;
import io.infectnet.server.engine.core.world.strategy.pathfinding.PathFinderStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldImpl extends World {
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
   * @param worldGeneratorStrategy the strategy to generate the world
   * @param pathFinderStrategy the strategy to find paths in the world
   */
  public WorldImpl(WorldGeneratorStrategy worldGeneratorStrategy,
                   PathFinderStrategy pathFinderStrategy) {
    super(worldGeneratorStrategy, pathFinderStrategy);

    entityPositionMap = new HashMap<>();
  }

  @Override
  public Set<Entity> seenBy(Entity entity) {

    int viewRadius = entity.getViewComponent().getViewRadius();
    Position position = entity.getPositionComponent().getPosition();

    return entitiesWithinRadius(viewRadius, position);
  }

  @Override
  public Set<Entity> neighboursOf(Entity entity) {
    Position position = entity.getPositionComponent().getPosition();

    return entitiesWithinRadius(1, position);
  }

  /**
   * Returns a set containing all Entities that are visible for the {@link Entity} given.
   * @param radius the distance it searches in
   * @param position the center of the search
   * @return a set of the found Entities
   */
  private Set<Entity> entitiesWithinRadius(int radius, Position position) {
    Set<Entity> set = new HashSet<>();

    ViewBox viewBox = new ViewBox(radius, position, this);

    for (int i = viewBox.northLimitHeight; i <= viewBox.southLimitHeight; ++i) {
      for (int j = viewBox.westLimitWidth; j <= viewBox.eastLimitWidth; ++j) {
        Entity en = tiles[i][j].getEntity();
        if (en != null && position.getH() != i && position.getW() != j) {
          set.add(en);
        }
      }
    }

    return set;
  }

  @Override
  public List<Tile> viewSight(Entity entity) {
    List<Tile> list = new ArrayList<>();
    int viewRadius = entity.getViewComponent().getViewRadius();
    Position position = entity.getPositionComponent().getPosition();

    ViewBox viewBox = new ViewBox(viewRadius, position, this);

    for (int i = viewBox.northLimitHeight; i <= viewBox.southLimitHeight; ++i) {
      for (int j = viewBox.westLimitWidth; j <= viewBox.eastLimitWidth; ++j) {

        list.add(tiles[i][j]);
      }
    }

    return list;
  }

  @Override
  public void generate(int height, int width) {
    tiles = new Tile[height][width];

    boolean[][] cells = worldGeneratorStrategy.generateWorld(height, width);

    this.height = height;
    this.width = width;

    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        if (isBorder(i, j)) {
          tiles[i][j] = new Tile(TileType.ROCK, new Position(i, j));
        } else if (cells[i][j] == worldGeneratorStrategy.CAVE) {
          tiles[i][j] = new Tile(TileType.CAVE, new Position(i, j));
        } else {
          tiles[i][j] = new Tile(TileType.ROCK, new Position(i, j));
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

  @Override
  public Tile getTileByPosition(Position position) {
    if (isPositionValidTile(position)) {
      return tiles[position.getH()][position.getW()];
    } else {
      throw new IllegalArgumentException("Invalid Position!");
    }
  }

  @Override
  public void setEntityOnPosition(Entity entity, Position position) {
    if (isPositionValidTile(position)) {
      getTileByPosition(position).setEntity(entity);
    } else {
      throw new IllegalArgumentException("Invalid Position!");
    }
  }

  @Override
  public boolean isPositionValidTile(Position position) {
    return position.getH() >= 0 && position.getH() < height && position.getW() >= 0
        && position.getW() < width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  /**
   * An inner class to represent the limitations of each Entity's view sight.
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
