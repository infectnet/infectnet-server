package io.infectnet.server.engine.core.util;

/**
 * Interface that indicates that the implementing class can be ordered based on the return value
 * of {@link #getOrder()}.
 * <p>
 * The lower the order value the higher priority the instance has.
 * </p>
 */
public interface Ordered {
  int getOrder();
}
