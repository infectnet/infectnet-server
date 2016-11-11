package io.infectnet.server.engine.entity.type;

import io.infectnet.server.engine.entity.component.TypeComponent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class TypeRepositoryImpl implements TypeRepository {
  private final Map<String, TypeComponent> typeMap;

  public TypeRepositoryImpl() {
    this.typeMap = new HashMap<>();
  }

  @Override
  public <T extends TypeComponent> void registerType(T typeComponent) {
    T type = Objects.requireNonNull(typeComponent);

    if (!typeMap.containsKey(type.getName())) {
      typeMap.put(type.getName(), type);
    } else {
      throw new NameAlreadyRegisteredException(type.getName());
    }
  }

  @Override
  public void removeTypeWithName(String name) {
    typeMap.remove(name);
  }

  @Override
  public Optional<TypeComponent> getTypeByName(String name) {
    return Optional.ofNullable(typeMap.get(name));
  }

  @Override
  public Set<TypeComponent> getRegisteredTypes() {
    return new HashSet<>(typeMap.values());
  }
}
