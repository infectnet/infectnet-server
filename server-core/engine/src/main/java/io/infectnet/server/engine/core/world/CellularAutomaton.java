package io.infectnet.server.engine.core.world;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import static java.lang.Math.random;

/**
 * This class represents a Cellular Automaton, which is used in the generation of the Map tiles.
 */
class CellularAutomaton implements MapGeneratorStrategy{
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
   * The minimum limit of the coherent cave system
   */
  public static final float RATIO_LIMIT = 0.6f;

  /**
   * The height of the map.
   */
  private int height;

  /**
   * The width of the map.
   */
  private int width;

  /**
   * The generated map.
   */
  private boolean[][] map;

  /**
   * The final product of the Automaton, with one cave system.
   */
  private boolean[][] finalMap;


  @Override
  public boolean[][] generateMap(int height, int width){
    this.height = height;
    this.width = width;

    map = new boolean[height][width];

    initializeMap();

    for(int i = 0; i < numberOfSteps; ++i){
      doSimulationStep();
    }

    map = eliminateIslands();

    return map;
  }

  /**
   * Initializes the array with true and false values placed at random.
   */
  private void initializeMap(){
    for(int x = 0; x < width; ++x){
      for(int y = 0; y < height; ++y){
        if(random() < chanceToStartAlive){
          map[y][x] = CAVE;
        }
      }
    }
  }

  /**
   * Performs a simulation step, in which it creates a new map using the values of the original,
   * now old map and the pre-set values of deathLimit and birthLimit.
   */
  private void doSimulationStep(){
    boolean[][] newMap = new boolean[height][width];

    for(int h = 0; h < height; ++h){
      for(int w = 0; w < width; ++w){
        int rockNeighbours = countRockNeighbours(h, w);

        if(map[h][w]){
          newMap[h][w] = rockNeighbours >= deathLimit;
        }
        else{
          newMap[h][w] = rockNeighbours > birthLimit;
        }
      }
    }
    map = newMap;
  }

  /**
   * Counts the number of cells in a ring around (x,y) that are alive.
   * @param x the first coordinate
   * @param y the second coordinate
   * @return the number of the alive cells found surrounding the (x,y) cell
   */
  private int countRockNeighbours(int x, int y){
    int count = 0;

    for(int i = -1; i < 2; ++i){
      for(int j = -1; j < 2; ++j){
        int neighbourX = x+i;
        int neighbourY = y+j;

        if(!isMiddleCell(i, j)){
          if(isInvalidCoordinate(neighbourX, neighbourY)){
            count = count + 1;
          } else if(map[neighbourX][neighbourY] == ROCK){
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
        neighbourX < 0 || neighbourY < 0 || neighbourX >= height || neighbourY >= width;
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
   */
  private boolean[][] eliminateIslands() {
    float ratio = 0f;
    Position startingPosition = new Position(0,0);

    while(ratio < RATIO_LIMIT){
      finalMap = new boolean[height][width];

      startingPosition = findNextCavePosition(startingPosition);
      if(startingPosition == null){
        break;
      }

      floodFillWholeCave(startingPosition);
      ratio = countCaveCellRatio();
    }

    return finalMap;
  }

  /**
   * Implementation of the classic flood-fill algorithm. It explores the cave system
   * from the starting position, storing the found positions in a new array called finalMap.
   * @param position the starting position
   */
  private void floodFillWholeCave(Position position){
    if(isInvalidCoordinate(position.getH(), position.getW())){
      return;
    }

    Queue<Position> queue = new LinkedList<>();
    Set<Position> set = new HashSet<>();

    queue.add(position);
    set.add(position);

    while(!queue.isEmpty()){
      Position pos = queue.remove();
      set.remove(pos);
      int h = pos.getH();
      int w = pos.getW();

      if(isNotVisitedCave(pos)){
        finalMap[h][w] = CAVE;
      }

      Position southPos = pos.stepSouth();
      Position westPos = pos.stepWest();
      Position northPos = pos.stepNorth();
      Position eastPos = pos.stepEast();

      if(canStep(set, southPos)){
        queue.add(southPos);
      }
      if(canStep(set, westPos)){
        queue.add(westPos);
      }
      if(canStep(set, northPos)){
        queue.add(northPos);
      }
      if(canStep(set, eastPos)){
        queue.add(eastPos);
      }
    }
  }

  private boolean canStep(Set<Position> set, Position pos) {
    return !isInvalidCoordinate(pos.getH(), pos.getW()) && isNotVisitedCave(pos) && set.add(pos);
  }

  /**
   * Checks if the Position given points to a cave in the map but to a rock on the new map.
   * @param pos the position to check
   * @return true if the value of the Position was not yet evaluated with the flood-fill algorithm, false otherwise.
   */
  private boolean isNotVisitedCave(Position pos) {
    return map[pos.getH()][pos.getW()] && !finalMap[pos.getH()][pos.getW()];
  }

  /**
   * Searches through the map for a position representing a cave, that have not been visited
   * by the algorithm.
   * @param oldPosition the previously found position
   * @return the new Position, which is a not yet visited cave
   */
  private Position findNextCavePosition(Position oldPosition){
    for(int i = oldPosition.getH(); i < height; ++i){
      for(int j = 0; j < width; ++j){
        if(map[i][j] && !finalMap[i][j]){
          return new Position(i,j);
        }
      }
    }
    return null;
  }

  /**
   * Counts the visited cave cells, stored in the finalMap array and calculates its ratio, compared to the whole array.
   * @return float the ratio of the cave system to all cells of the map.
   */
  private float countCaveCellRatio() {
    float counter = 0;
    float numberOfAllCells = height*width;

    for(int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        if(finalMap[i][j]){
          counter++;
        }
      }
    }

    return counter / numberOfAllCells;
  }

  /**
   * An inner class to hold two coordinates together.
   */
  private class Position{
    private final int h;
    private final int w;

    Position(int h, int w){
      this.h = h;
      this.w = w;
    }

    /**
     * Creates this Position's neighbour, stepping one step south.
     * @return the southern neighbour
     */
    Position stepSouth(){
      return new Position(h+1, w);
    }

    /**
     * Creates this Position's neighbour, stepping one step west.
     * @return the western neighbour
     */
    Position stepWest(){
      return new Position(h, w-1);
    }

    /**
     * Creates this Position's neighbour, stepping one step north.
     * @return the northern neighbour
     */
    Position stepNorth(){
      return new Position(h-1, w);
    }

    /**
     * Creates this Position's neighbour, stepping one step east.
     * @return the eastern neighbour
     */
    Position stepEast(){
      return new Position(h, w+1);
    }

    int getH() {
      return h;
    }

    int getW() {
      return w;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Position position = (Position) o;

      if (h != position.h) return false;
      return w == position.w;

    }

    @Override
    public int hashCode() {
      int result = h;
      result = 31 * result + w;
      return result;
    }
  }
}
