package io.infectnet.server.engine.core.configuration;

import dagger.Module;
import dagger.Provides;
import io.infectnet.server.engine.core.world.World;
import io.infectnet.server.engine.core.world.strategy.generation.WorldGeneratorStrategy;
import io.infectnet.server.engine.core.world.WorldImpl;
import io.infectnet.server.engine.core.world.strategy.generation.CellularAutomaton;

import javax.inject.Singleton;

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
}
