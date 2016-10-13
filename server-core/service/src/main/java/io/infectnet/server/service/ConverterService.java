package io.infectnet.server.service;

import java.util.List;

/**
 * Interface for services handling object conversion between persistence and service layer
 */
public interface ConverterService {

  /**
   * Converts an object to the given target class, if appropriate converter mapping was provided.
   * @param source the object to be converted
   * @param targetClass the target class
   * @param <S> source class
   * @param <T> target class
   * @return the converted object
   */
  <S, T> T map(S source, Class<T> targetClass);

  /**
   * Converts a list of objects to the given target class, if appropriate converter mapping was
   * provided.
   * @param sourceList the list of object to be converted
   * @param targetClass the target class
   * @param <S> source class
   * @param <T> target class
   * @return a list of converted objects
   */
  <S, T> List<T> map(List<S> sourceList, Class<T> targetClass);

  /**
   * Adds a converter mapping to the converter service.
   * @param converter the converter which implements the conversion
   * @param <S> source class
   * @param <T> target class
   */
  <S, T> void addConverterMapping(Converter<S, T> converter);
}
