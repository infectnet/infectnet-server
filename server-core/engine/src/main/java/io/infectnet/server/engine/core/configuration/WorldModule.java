package io.infectnet.server.engine.core.configuration;

import io.infectnet.server.engine.core.entity.type.TypeRepository;
import io.infectnet.server.engine.core.world.World;
import io.infectnet.server.engine.core.world.WorldImpl;
import io.infectnet.server.engine.core.world.customizer.ResourceCustomizer;
import io.infectnet.server.engine.core.world.customizer.WorldCustomizer;
import io.infectnet.server.engine.core.world.strategy.generation.CellularAutomaton;
import io.infectnet.server.engine.core.world.strategy.generation.WorldGeneratorStrategy;
import io.infectnet.server.engine.core.world.strategy.pathfinding.AStarPathFinderStrategy;
import io.infectnet.server.engine.core.world.strategy.pathfinding.Heuristic;
import io.infectnet.server.engine.core.world.strategy.pathfinding.PathFinderStrategy;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class WorldModule {
  @Provides
  @Singleton
  public static World providesWorld(WorldGeneratorStrategy strategy) {
    return new WorldImpl(strategy);
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
  @IntoSet
  public static WorldCustomizer providesResourceCustomizer(TypeRepository typeRepository) {
    return new ResourceCustomizer(typeRepository);
  }

}
