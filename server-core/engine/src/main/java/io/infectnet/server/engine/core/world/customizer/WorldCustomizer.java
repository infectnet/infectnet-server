package io.infectnet.server.engine.core.world.customizer;

import io.infectnet.server.engine.core.world.World;

/**
 * Interface for customizing the World after it has been generated.
 */
public interface WorldCustomizer {
  
  /**
   * Customizes the world in a specified way.
   * @param world the world to be changed or updated
   */
  void customize(World world);
}
