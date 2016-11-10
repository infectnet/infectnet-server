package io.infectnet.server.engine.entity;

import io.infectnet.server.engine.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class EntityManagerImpl implements EntityManager {
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
  }

  private void detachEntity(Entity entity) {
  }

  public static class Query implements EntityManager.Query {
    private final EntityManagerImpl entityManager;

    private Optional<Player> playerSelector;

    private Optional<Category> categorySelector;

    private final List<Predicate<Entity>> predicateList;

    public Query(EntityManagerImpl entityManager) {
      this.entityManager = entityManager;

      playerSelector = Optional.empty();

      categorySelector = Optional.empty();

      predicateList = new ArrayList<>();
    }

    @Override
    public EntityManager.Query ofPlayer(Player player) {
      playerSelector = Optional.of(Objects.requireNonNull(player));

      return this;
    }

    @Override
    public EntityManager.Query inCategory(Category category) {
      categorySelector = Optional.of(Objects.requireNonNull(category));

      return this;
    }

    @Override
    public EntityManager.Query satisfying(Predicate<Entity> predicate) {
      predicateList.add(Objects.requireNonNull(predicate));

      return this;
    }

    @Override
    public Collection<Entity> execute() {
      if (!playerSelector.isPresent() || !categorySelector.isPresent()) {
        throw new IllegalStateException("Player and Category selectors cannot be omitted!");
      }

      return null;
    }
  }
}
