package io.infectnet.server.engine.core.world;

import static java.lang.Math.random;

/**
 * This class represents a Cellular Automaton, which is used in the generation of the Map tiles.
 */
class CellularAutomaton {

  /**
   * Sets how dense the initial grid is with living cells.
   */
  private static final float chanceToStartAlive = 0.4f;

  /**
   * The number of neighbours that cause an alive cell to die.
   */
  private static final int deathLimit = 4;

  /**
   * The number of neighbours that cause a dead cell to become alive.
   */
  private static final int birthLimit = 4;

  /**
   * The number of times we perform the simulation step.
   */
  private static final int numberOfSteps = 3;

  /**
   * The height of the map.
   */
  private final int height;

  /**
   * The width of the map.
   */
  private final int width;

  /**
   * The generated map.
   */
  private boolean[][] map;

  /**
   * The final product of the Automaton, with one cave system.
   */
  private boolean[][] finalMap;

  /**
   * Creates a Cellular Automaton with the limiting numbers.
   * @param height the height of the map
   * @param width the width of the map
   */
  CellularAutomaton(int height, int width) {
    this.height = height;
    this.width = width;
  }

  /**
   * Generates a much simplified form of a {@link Map}, using booleans instead of {@link TileType}s.
   * @return a boolean array containing the data about all tiles that were generated.
   */
  boolean[][] generateMap(){
    boolean[][] map = new boolean[height][width];

    map = initialiseMap(map);

    for(int i = 0; i < numberOfSteps; ++i){
      map = doSimulationStep(map);
    }

    map = eliminateIslands(map);

    //TODO check if the cave system is big enough
    return map;
  }

  /**
   * Initializes the array with true and false values placed at random.
   * @param map the array to initialize
   * @return the initialized array, with true and false values
   */
  private boolean[][] initialiseMap(boolean[][] map){
    for(int x = 0; x < width; ++x){
      for(int y = 0; y < height; ++y){
        if(random() < chanceToStartAlive){
          map[y][x] = true;
        }
      }
    }
    return map;
  }

  /**
   * Performs a simulation step, in which it creates a new map using the values of the original,
   * now old map and the pre-set values of deathLimit and birthLimit.
   * @param oldMap the array containing the value of the map before the step
   * @return a new array containing the values calculated by the values of the old map
   */
  private boolean[][] doSimulationStep(boolean[][] oldMap){
    boolean[][] newMap = new boolean[height][width];

    for(int y = 0; y < oldMap.length; ++y){
      for(int x = 0; x < oldMap[0].length; ++x){
        int aliveNeighbours = countAliveNeighbours(oldMap, x, y);

        if(oldMap[y][x]){
          newMap[y][x] = aliveNeighbours >= deathLimit;
        }
        else{
          newMap[y][x] = aliveNeighbours > birthLimit;
        }
      }
    }
    return newMap;
  }

  /**
   * Counts the number of cells in a ring around (x,y) that are alive.
   * @param map the array of the map
   * @param x the first coordinate
   * @param y the second coordinate
   * @return the number of the alive cells found surrounding the (x,y) cell
   */
  private int countAliveNeighbours(boolean[][] map, int x, int y){
    int count = 0;

    for(int i = -1; i < 2; ++i){
      for(int j = -1; j < 2; ++j){
        int neighbourX = x+i;
        int neighbourY = y+j;

        if(!isMiddleCell(i, j)){
          if(isInvalidCoordinate(neighbourX, neighbourY)){
            count = count + 1;
          } else if(map[neighbourX][neighbourY]){
            count = count + 1;
          }
        }
      }
    }

    return count;
  }

  /**
   * Checks if the neighbour cell is beyond the borders of the map.
   * @param neighbourX the x coordinate of the neighbour
   * @param neighbourY the y coordinate of the neighbour
   * @return true if it is off the grid, and false otherwise
   */
  private boolean isInvalidCoordinate(int neighbourX, int neighbourY) {
    return
        neighbourX < 0 || neighbourY < 0 || neighbourX >= width || neighbourY >= height;
  }

  /**
   * Checks if the cell is int the middle of the predefined grid, containing 9 cells.
   * @param i the x coordinate
   * @param j the y coordinate
   * @return true if it is the middle cell, false otherwise
   */
  private boolean isMiddleCell(int i, int j) {
    return i == 0 && j == 0;
  }

  /**
   * Creates a new map without the isolated caves, which were on the previous map.
   * @param oldMap the array with the generated values
   * @return a new array containing only one cave system
   */
  private boolean[][] eliminateIslands(boolean[][] oldMap) {
    boolean[][] newMap = new boolean[height][width];
    //TODO define  a starting point!!!
    newMap = floodFillWholeCave(oldMap, newMap);

    return newMap;
  }

  private boolean[][] floodFillWholeCave(boolean[][] oldMap, boolean[][] newMap){
    //TODO
    return newMap;
  }
}
