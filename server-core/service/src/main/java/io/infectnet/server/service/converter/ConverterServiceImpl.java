package io.infectnet.server.service.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default {@link ConverterService} implementation.
 */
public class ConverterServiceImpl implements ConverterService {

  private static final Logger logger = LoggerFactory.getLogger(ConverterServiceImpl.class);

  private static final String NO_SUCH_CONVERTER_MAPPING = "No such converter mapping can be found!";

  // Sorted by source classes
  private Map<Class<?>, List<Converter<?, ?>>> mappings;

  public ConverterServiceImpl() {
    this.mappings = new HashMap<>();
  }

  @Override
  public <S, T> T map(S source, Class<T> targetClass) {
    S nonNullSource = Objects.requireNonNull(source);

    // This unchecked cast is safe, since we know the
    // source's class, because it's given as an argument.
    @SuppressWarnings("unchecked")
    Optional<Converter<S, T>>
        converter =
        getConverterMapping((Class<S>) nonNullSource.getClass(),
            Objects.requireNonNull(targetClass));

    if (!converter.isPresent()) {
      throw new IllegalArgumentException(NO_SUCH_CONVERTER_MAPPING);
    }

    return converter.get().convert(nonNullSource);
  }

  @Override
  public <S, T> List<T> map(List<S> sourceList, Class<T> targetClass) {
    return Objects.requireNonNull(sourceList).stream()
        .map(s -> map(s, Objects.requireNonNull(targetClass)))
        .collect(Collectors.toList());
  }

  @Override
  public <S, T> void addConverterMapping(Converter<S, T> converter) {
    Converter<S, T> nonNullConverter = Objects.requireNonNull(converter);

    if (mappings.containsKey(nonNullConverter.getSourceClass())) {

      if (!getConverterMapping(nonNullConverter.getSourceClass(), nonNullConverter.getTargetClass())
          .isPresent()) {
        mappings.get(nonNullConverter.getSourceClass()).add(nonNullConverter);
      } else {
        throw new MappingAlreadyReservedException(nonNullConverter.getSourceClass(),
            nonNullConverter.getTargetClass());
      }

    } else {
      mappings.put(nonNullConverter.getSourceClass(), Collections.singletonList(nonNullConverter));
    }

    logger.info("New converter mapping added: {}", nonNullConverter);
  }

  /**
   * Returns a {@link Converter} identified by it's source and target class.
   * @param sourceClass the requested converter mapping's source class
   * @param targetClass the requested converter mapping's target class
   * @param <S> source class
   * @param <T> target class
   * @return the appropriate converter mapping
   */
  <S, T> Optional<Converter<S, T>> getConverterMapping(Class<S> sourceClass, Class<T> targetClass) {

    if (mappings.containsKey(Objects.requireNonNull(sourceClass))) {

      // The unchecked cast is completely safe, since at that point
      // we know that the source and target class of the converter
      // match our needs.
      @SuppressWarnings("unchecked")
      Optional<Converter<S, T>> converterMapping = mappings.get(sourceClass).stream()
          .filter(c -> c.getTargetClass().equals(Objects.requireNonNull(targetClass)))
          .map(c -> (Converter<S, T>) c)
          .findFirst();
      return converterMapping;

    } else {
      return Optional.empty();
    }
  }
}
