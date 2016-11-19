package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;

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
   * The HashMap containing all Entities which are on the World,
   * with the Entity as the key, and a position as value.
   */
  private final HashMap<Entity,Tile> entityPositionMap;

  /**
   * Creates a new World in a size defined by the parameters. All its tiles are generated at random.
   * @param strategy the strategy to generate the world
   */
  public WorldImpl(WorldGeneratorStrategy strategy) {
    this.strategy = strategy;

    entityPositionMap = new HashMap<>();
  }

  @Override
  public List<Entity> listOfEntitiesVisible(Entity entity) {
    return null;
  }

  /**
   * Generates a new array of Tiles with the given strategy.
   * @param height the height of the world
   * @param width the width of the world
   */
  @Override
  public void generate(int height, int width) {
    tiles = new Tile[height][width];

    boolean[][] cells = strategy.generateMap(height, width);

    for(int i = 0; i < height; ++i){
      for(int j = 0; j < width; ++j){
        if(isBorder(i,j)){
          tiles[i][j] = new Tile(TileType.ROCK);
        } else if(cells[i][j] == strategy.CAVE){
          tiles[i][j] = new Tile(TileType.CAVE);
        } else {
          tiles[i][j] = new Tile(TileType.ROCK);
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
}
