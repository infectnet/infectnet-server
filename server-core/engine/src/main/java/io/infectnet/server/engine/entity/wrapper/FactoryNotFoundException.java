package io.infectnet.server.engine.entity.wrapper;

import io.infectnet.server.engine.entity.component.TypeComponent;

public class FactoryNotFoundException extends RuntimeException {
  private static final String MESSAGE = "Wrapper factory not found for TypeComponent";

  private final TypeComponent typeComponent;

  public FactoryNotFoundException(TypeComponent typeComponent) {
    super(MESSAGE);

    this.typeComponent = typeComponent;
  }

  public TypeComponent getTypeComponent() {
    return typeComponent;
  }
}
