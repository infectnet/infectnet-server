package io.infectnet.server.engine.entity;

/**
 * Basic categories for {@link Entity} objects.
 */
public enum Category {
  /**
   * Category for entities capable of collectiong resources, building various buildings and
   * things like that.
   */
  WORKER,

  /**
   * Entities in this category can attack other entities or make discoveries in the map.
   */
  FIGHTER,

  /**
   * Building entities are static objects that have some special abilities, like spawning
   * other entities.
   */
  BUILDING,

  /**
   * Entities in this category represent collectible resources.
   */
  RESOURCE,

  /**
   * Special category is for entities that have some special meaning. For example a rally point.
   */
  SPECIAL
}
