package io.infectnet.server.engine.core.entity;

import io.infectnet.server.engine.core.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class EntityManagerImpl implements EntityManager {
  /**
   * Store by username instead of by {@link Player} instance because {@code String}
   * is immutable so hashCode and equals values won't change.
   */
  private final Map<String, PlayerStorage> playerMap;

  public EntityManagerImpl() {
    this.playerMap = new HashMap<>();
  }

  @Override
  public Query query() {
    return new Query(this);
  }

  @Override
  public void addEntity(Entity entity) {
    manageEntity(Objects.requireNonNull(entity));
  }

  @Override
  public void removeEntity(Entity entity) {
    detachEntity(Objects.requireNonNull(entity));
  }

  private void manageEntity(Entity entity) {
    PlayerStorage storage = createOrGetPlayerStorage(entity.getOwnerComponent().getOwner());

    storage.storeEntity(entity);
  }

  private void detachEntity(Entity entity) {
    PlayerStorage storage = getPlayerStorage(entity.getOwnerComponent().getOwner());

    storage.removeEntity(entity);
  }

  private PlayerStorage createOrGetPlayerStorage(Player player) {
    PlayerStorage storage = playerMap.get(player.getUsername());

    if (storage == null) {
      storage = new PlayerStorage();

      playerMap.put(player.getUsername(), storage);
    }

    return storage;
  }

  private PlayerStorage getPlayerStorage(Player player) {
    return playerMap.get(player.getUsername());
  }

  private Collection<Entity> executeQuery(Query query) {
    PlayerStorage storage = createOrGetPlayerStorage(query.playerSelector);

    return storage.executeQuery(query);
  }

  public static class Query implements EntityManager.Query {
    private final EntityManagerImpl entityManager;

    private Player playerSelector;

    private Category categorySelector;

    private final List<Predicate<Entity>> predicateList;

    private Query(EntityManagerImpl entityManager) {
      this.entityManager = entityManager;

      playerSelector = null;

      categorySelector = null;

      predicateList = new ArrayList<>();
    }

    @Override
    public EntityManager.Query ofPlayer(Player player) {
      playerSelector = Objects.requireNonNull(player);

      return this;
    }

    @Override
    public EntityManager.Query inCategory(Category category) {
      categorySelector = Objects.requireNonNull(category);

      return this;
    }

    @Override
    public EntityManager.Query satisfying(Predicate<Entity> predicate) {
      predicateList.add(Objects.requireNonNull(predicate));

      return this;
    }

    @Override
    public Collection<Entity> execute() {
      if (playerSelector == null || categorySelector == null) {
        throw new IllegalStateException("Player and Category selectors cannot be omitted!");
      }

      return entityManager.executeQuery(this);
    }
  }

  private static class PlayerStorage {
    private final Map<Category, List<Entity>> entityMap;

    private PlayerStorage() {
      this.entityMap = new EnumMap<>(Category.class);

      for (Category category : Category.values()) {
        entityMap.put(category, new LinkedList<>());
      }
    }

    private void storeEntity(Entity entity) {
      List<Entity> entities = entityMap.get(entity.getTypeComponent().getCategory());

      if (entities != null) {
        entities.add(entity);
      }
    }

    private void removeEntity(Entity entity) {
      List<Entity> entities = entityMap.get(entity.getTypeComponent().getCategory());

      if (entities != null) {
        entities.remove(entity);
      }
    }

    private List<Entity> executeQuery(Query query) {
      // Always non-null - hopefully
      List<Entity> entities = entityMap.get(query.categorySelector);

      return filterEntities(entities, query);
    }

    private List<Entity> filterEntities(List<Entity> entities, Query query) {
      List<Entity> results = new LinkedList<>();

      for (Entity entity : entities) {
        if (matchPredicates(entity, query.predicateList)) {
          results.add(entity);
        }
      }

      return results;
    }

    private boolean matchPredicates(Entity entity, List<Predicate<Entity>> predicateList) {
      for (Predicate<Entity> predicate : predicateList) {
        if (!predicate.test(entity)) {
          return false;
        }
      }

      return true;
    }
  }
}
