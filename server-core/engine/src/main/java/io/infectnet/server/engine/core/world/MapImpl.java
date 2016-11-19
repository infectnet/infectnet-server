package io.infectnet.server.engine.core.world;

import io.infectnet.server.engine.core.entity.Entity;

import java.util.HashMap;
import java.util.List;

public class MapImpl implements Map {
  /**
   * The two-dimensional array to hold all tiles in the {@link Map}
   */
  private Tile[][] tiles;

  /**
   * The HashMap containing all Entities which are on the Map,
   * with the Entity as the key, and a position as value.
   */
  private HashMap<Entity,Tile> entityPositionMap;

  /**
   * The height of the map, the number pf positions available on the y-axis.
   */
  private final int height;

  /**
   * The width of the map, the number pf positions available on the x-axis.
   */
  private final int width;

  /**
   * Creates a new Map in a size defined by the parameters. All its tiles are generated at random.
   * @param height limitation of the number of tiles on the y-axis
   * @param width limitation of the number of tiles on x-axis
   */
  public MapImpl(int height, int width) {
    this.height = height;
    this.width = width;

    tiles = new Tile[height][width];
    entityPositionMap = new HashMap<>();

    generateNewMap();
  }

  @Override
  public List<Entity> listOfEntitiesVisible(Entity entity) {
    //TODO
    return null;
  }

  /**
   * Generates a new array of Tiles with a Cellular Automaton.
   */
  private void generateNewMap() {
    CellularAutomaton cellularAutomaton = new CellularAutomaton(height, width);

    boolean[][] cells = cellularAutomaton.generateMap();

    for(int i = 0; i < height; ++i){
      for(int j = 0; j < width; ++j){
        if(isBorder(i,j)){
          tiles[i][j] = new Tile(TileType.ROCK);
        } else if(cells[i][j]){
          tiles[i][j] = new Tile(TileType.CAVE);
        } else {
          tiles[i][j] = new Tile(TileType.ROCK);
        }
      }
    }
  }

  /**
   * Checks if the given coordinates are on the edge of the Map.
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

  public void addEntityOnTile(Entity entity){
    //TODO add PositionComponent to Entity
    //TODO entityPositionMap.put(entity, entity.getPositionComponent().getTile())
  }
}
