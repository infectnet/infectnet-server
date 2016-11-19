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
   * The first type of cell, that the map consist of.
    */
  private static final boolean CAVE = true;

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
  @Override
  public boolean[][] generateMap(){
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

    for(int y = 0; y < map.length; ++y){
      for(int x = 0; x < map[0].length; ++x){
        int aliveNeighbours = countAliveNeighbours(x, y);

        if(map[y][x]){
          newMap[y][x] = aliveNeighbours >= deathLimit;
        }
        else{
          newMap[y][x] = aliveNeighbours > birthLimit;
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
  private int countAliveNeighbours(int x, int y){
    int count = 0;

    for(int i = -1; i < 2; ++i){
      for(int j = -1; j < 2; ++j){
        int neighbourX = x+i;
        int neighbourY = y+j;

        if(!isMiddleCell(i, j)){
          if(isInvalidCoordinate(neighbourX, neighbourY)){
            count = count + 1;
          } else if(!map[neighbourX][neighbourY]){
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
   */
  private boolean[][] eliminateIslands() {
    float ratio = 0f;
    Position startingPosition = new Position(0,0);

    while(ratio < 0.6f){
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
    if(!isValidPosition(position)){
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

      if(isValidPosition(southPos) && isNotVisitedCave(southPos) && set.add(southPos)){
        queue.add(southPos);
      }
      if(isValidPosition(westPos) && isNotVisitedCave(westPos) && set.add(westPos)){
        queue.add(westPos);
      }
      if(isValidPosition(northPos) && isNotVisitedCave(northPos) && set.add(northPos)){
        queue.add(northPos);
      }
      if(isValidPosition(eastPos) && isNotVisitedCave(eastPos) && set.add(eastPos)){
        queue.add(eastPos);
      }
    }
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
   * Checks if the Position is on the map, so not beyond its borders
   * @param position the position to check
   * @return true if it is valid, false otherwise
   */
  private boolean isValidPosition(Position position) {
    return position.getH() < height && position.getW() < width && position.getH() >= 0 && position.getW() >= 0;
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
    private int h;
    private int w;

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
