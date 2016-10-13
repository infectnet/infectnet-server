package io.infectnet.server.service.exception;

/**
 * Exception thrown, when a converter mapping is already reserved,
 * but we wanted to create a new one using it.
 */
public class MappingAlreadyReservedException extends RuntimeException {

  private static final String MAPPING_IS_ALREADY_RESERVED = "Mapping is already reserved: ";

  private Class<?> sourceClass;

  private Class<?> targetClass;

  /**
   * Contstructs a new converter mapping reserved exception.
   * @param sourceClass the reserved converter mapping's source class
   * @param targetClass the reserved converter mapping's target class
   */
  public MappingAlreadyReservedException(Class<?> sourceClass, Class<?> targetClass) {
    super(MAPPING_IS_ALREADY_RESERVED + sourceClass + " -> " + targetClass);

    this.sourceClass = sourceClass;
    this.targetClass = targetClass;
  }

  /**
   * Returns the colliding converter mapping's source class.
   * @return the source class
   */
  public Class<?> getSourceClass() {
    return sourceClass;
  }


  /**
   * Returns the colliding converter mapping's target class.
   * @return the target class
   */
  public Class<?> getTargetClass() {
    return targetClass;
  }
}
