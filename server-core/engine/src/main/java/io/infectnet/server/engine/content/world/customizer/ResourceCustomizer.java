package io.infectnet.server.engine.content.world.customizer;

import io.infectnet.server.engine.content.type.BitResourceTypeComponent;
import io.infectnet.server.engine.core.entity.Entity;
import io.infectnet.server.engine.core.entity.component.TypeComponent;
import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.player.PlayerService;
import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.Tile;
import io.infectnet.server.engine.core.world.TileType;
import io.infectnet.server.engine.core.world.World;
import io.infectnet.server.engine.core.world.customizer.WorldCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Customizing the World after the Tiles were generated,
 * putting Resources onto some chosen Tiles.
 */
public class ResourceCustomizer implements WorldCustomizer {
  /**
   * The number of neighbouring CAVE Tiles and other Resources.
   */
  private static final int RESOURCE_LIMIT = 8;

  /**
   * The number of times we will execute the resource generation.
   * This is for a resource to be surrounded with other resources,
   * creating groups, not just scattered resources all around the World.
   */
  private static final int GENERATION_LIMIT = 2;

  private static final String ENV_PLAYER_NAME = "Environment";

  private final TypeRepository typeRepository;

  private final PlayerService playerService;

  private TypeComponent component;

  private Player environmentPlayer;

  public ResourceCustomizer(TypeRepository typeRepository, PlayerService playerService) {
    this.typeRepository = typeRepository;
    this.playerService = playerService;
  }

  @Override
  public void customize(World world) {
    Optional<TypeComponent>
        componentOptional =
        typeRepository.getTypeByName(BitResourceTypeComponent.TYPE_NAME);

    Optional<Player> environmentPlayerOptional = playerService.getPlayerByUsername(ENV_PLAYER_NAME);

    if (componentOptional.isPresent()) {
      component = componentOptional.get();

      if (environmentPlayerOptional.isPresent()) {
        environmentPlayer = environmentPlayerOptional.get();

        for (int i = 0; i < GENERATION_LIMIT; ++i) {
          generate(world);
        }
      }
    }
  }

  /**
   * Generating the resources, collecting them into a list.
   * This is similar to the cellular automaton's simulationStep method.
   * @param world the world to generate the resources into
   */
  private void generate(World world) {
    List<Position> resources = new ArrayList<>();

    for (int h = 1; h < world.getHeight() - 1; ++h) {
      for (int w = 1; w < world.getWidth() - 1; ++w) {

        Position current = new Position(h, w);

        int caveAndResourceNeighbours = countCaveAndResourceNeighbours(world, current);

        if (isValidResourcePosition(world.getTileByPosition(current), caveAndResourceNeighbours)) {
          resources.add(current);
        }
      }
    }

    addingResourcesToWorld(world, resources);
  }

  /**
   * Checks if the given tile meets the expectations to place a Resource.
   * @param tile the given tile to check
   * @param caveAndResourceNeighbours number of CAVE and Resource neighbours
   * @return true if the Tile is valid, false otherwise
   */
  private boolean isValidResourcePosition(Tile tile, int caveAndResourceNeighbours) {
    return tile.getType() == TileType.ROCK
        && caveAndResourceNeighbours >= RESOURCE_LIMIT;
  }

  /**
   * Adds new Resources into the specified Tiles of the World.
   * @param world the world to place the new Resources into
   * @param resources the Entities to place into the World
   */
  private void addingResourcesToWorld(World world, List<Position> resources) {
    for (Position pos : resources) {
      Entity resource = component.createEntityOfType();

      resource.getPositionComponent().setPosition(pos);
      resource.getOwnerComponent().setOwner(environmentPlayer);

      world.getTileByPosition(pos).setEntity(resource);
    }
  }

  /**
   * Counts the number of neighbours surrounding the given Position
   * that are either of type CAVE or contains an Entity,
   * which could only be a previously placed Resource.
   * @param world the given world
   * @param current the given Position
   * @return the number of the CAVE and Resource cells found surrounding the given Position
   */
  private int countCaveAndResourceNeighbours(World world, Position current) {
    int count = 0;

    for (Position neighbour : current.getNeighbours()) {
      if (!world.isPositionValidTile(neighbour)) {
        count++;
      } else if (world.getTileByPosition(neighbour).getType() == TileType.CAVE
          || world.getTileByPosition(neighbour).getEntity() != null) {
        count++;
      }
    }

    return count;
  }
}
