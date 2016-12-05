package io.infectnet.server.engine.core.entity.type;

import io.infectnet.server.engine.core.entity.component.TypeComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class TypeRepositoryImpl implements TypeRepository {

  private static final Logger logger = LoggerFactory.getLogger(TypeRepositoryImpl.class);

  private final Map<String, TypeComponent> typeMap;

  public TypeRepositoryImpl() {
    this.typeMap = new HashMap<>();
  }

  @Override
  public <T extends TypeComponent> void registerType(T typeComponent) {
    T type = Objects.requireNonNull(typeComponent);

    if (!typeMap.containsKey(type.getName())) {
      typeMap.put(type.getName(), type);

      logger.info("Registered new TypeComponent with name: {}", type.getName());
    } else {
      throw new NameAlreadyRegisteredException(type.getName());
    }
  }

  @Override
  public void removeTypeWithName(String name) {
    TypeComponent removedTypeComponent = typeMap.remove(name);

    logger.info("Removed registered TypeComponent: {}", removedTypeComponent);
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
