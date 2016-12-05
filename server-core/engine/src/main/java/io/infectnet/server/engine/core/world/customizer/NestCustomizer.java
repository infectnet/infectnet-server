package io.infectnet.server.engine.core.world.customizer;

import io.infectnet.server.engine.core.entity.Category;
import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Customizing the World after the Tiles were generated,
 * finding all Tiles that are fitted to hold a Nest.
 */
public class NestCustomizer implements WorldCustomizer {

  /**
   * The minimum distance of two Nests.
   */
  private static final int NEST_DISTANCE_LIMIT = 4;

  /**
   * All possible Positions to hold a Nest.
   */
  private List<Position> basePositions;

  @Override
  public void customize(World world) {
    basePositions = new ArrayList<>();

    for (int h = 1; h < world.getHeight() - 1; ++h) {
      for (int w = 1; w < world.getWidth() - 1; ++w) {

        Position current = new Position(h, w);

        if (isValidNestPosition(world, current)) {
          basePositions.add(current);
        }
      }
    }
  }

  /**
   * Checks if the Position meets all conditions to hold a Nest.
   * @param world the world given
   * @param current the Position to check
   * @return true if the Position is valid, false otherwise
   */
  private boolean isValidNestPosition(World world, Position current) {
    return !world.getTileByPosition(current).isBlockedOrOccupied()
        && isBorderEmptyAndResourceAvailable(world, current);
  }

  /**
   * Checks if the Position is surrounded by at least two layers of CAVE type Tiles,
   * and at least one Resource is available in three steps.
   * @param world the world given
   * @param current the Position to check
   * @return true if both conditions are met, false otherwise
   */
  private boolean isBorderEmptyAndResourceAvailable(World world, Position current) {
    Set<Position> twoLayerNeighbours = new HashSet<>();

    for (Position neighbour : current.getNeighbours()) {

      if (world.isPositionValidTile(neighbour)) {
        twoLayerNeighbours.add(neighbour);

        for (Position secondNeighbour : neighbour.getNeighbours()) {

          if (world.isPositionValidTile(secondNeighbour)) {
            twoLayerNeighbours.add(secondNeighbour);
          }
        }
      }
    }

    boolean valid = true;

    for (Position pos : twoLayerNeighbours) {
      if (world.getTileByPosition(pos).isBlockedOrOccupied()) {
        valid = false;
      }
    }

    if (valid) {

      Set<Position> thirdLayer = new HashSet<>();

      for (Position pos : twoLayerNeighbours) {

        for (Position thirdNeighbour : pos.getNeighbours()) {
          if (world.isPositionValidTile(thirdNeighbour)) {
            thirdLayer.add(thirdNeighbour);
          }
        }
      }

      thirdLayer.removeAll(twoLayerNeighbours);

      for (Position pos : thirdLayer) {
        if (world.getTileByPosition(pos).getEntity() != null
            && world.getTileByPosition(pos).getEntity().getTypeComponent().getCategory()
            == Category.RESOURCE) {

          return true;
        }
      }

      return false;
    }

    return false;
  }

  /**
   * Returns a Position to place a Nest, chosen at random.
   * @return the chosen Position
   */
  public Position getRandomNestPosition() {
    Position base = basePositions.get(new Random().nextInt(basePositions.size() - 1));
    basePositions.remove(base);

    Iterator<Position> it = basePositions.iterator();

    while (it.hasNext()) {
      if (isNestTooClose(base, it.next())) {
        it.remove();
      }
    }

    return base;
  }

  /**
   * Checks if the given two Nests are too close to each other.
   * @param pos1 the first Position given
   * @param pos2 the second Position given
   * @return true if the two are too close to each other, false otherwise
   */
  private boolean isNestTooClose(Position pos1, Position pos2) {
    return getDistance(pos1, pos2) <= NEST_DISTANCE_LIMIT;
  }

  /**
   * Calculates the estimated distance of two Positions.
   * @param pos1 the first Position given
   * @param pos2 the second Position given
   * @return the estimated distance
   */
  private int getDistance(Position pos1, Position pos2) {
    return Math.abs(pos1.getH() - pos2.getH()) + Math.abs(pos1.getW() - pos2.getW());
  }
}

