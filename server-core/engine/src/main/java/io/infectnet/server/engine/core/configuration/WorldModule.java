package io.infectnet.server.engine.core.configuration;

import io.infectnet.server.engine.core.world.World;
import io.infectnet.server.engine.core.world.WorldImpl;
import io.infectnet.server.engine.core.world.strategy.generation.CellularAutomaton;
import io.infectnet.server.engine.core.world.strategy.generation.WorldGeneratorStrategy;
import io.infectnet.server.engine.core.world.strategy.pathfinding.AStarPathFinderStrategy;
import io.infectnet.server.engine.core.world.strategy.pathfinding.Heuristic;
import io.infectnet.server.engine.core.world.strategy.pathfinding.PathFinderStrategy;
import io.infectnet.server.engine.core.world.strategy.pathfinding.WeightedHeuristic;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class WorldModule {
  @Provides
  @Singleton
  public static World providesWorld(WorldGeneratorStrategy worldGeneratorStrategy,
                                    PathFinderStrategy pathFinderStrategy) {
    return new WorldImpl(worldGeneratorStrategy, pathFinderStrategy);
  }

  @Provides
  @Singleton
  public static WorldGeneratorStrategy providesWorldGeneratorStrategy() {
    return new CellularAutomaton();
  }

  @Provides
  @Singleton
  public static PathFinderStrategy providesPathFinderStrategy(Heuristic heuristic) {
    return new AStarPathFinderStrategy(heuristic);
  }

  @Provides
  @Singleton
  public static Heuristic providesHeuristic() {
    return new WeightedHeuristic();
  }
}
