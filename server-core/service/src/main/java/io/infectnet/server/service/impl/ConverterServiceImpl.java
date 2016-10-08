package io.infectnet.server.service.impl;

import io.infectnet.server.service.Converter;
import io.infectnet.server.service.ConverterService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Default {@link ConverterService} implementation.
 */
public class ConverterServiceImpl implements ConverterService {

    // Sorted by source classes
    private Map<Class<?>, List<Converter<?, ?>>> mappings;

    public ConverterServiceImpl() {
        this.mappings = new HashMap<>();
    }

    @Override
    public <S, T> T map(S source, Class<T> targetClass) {
        S nonNullSource = Objects.requireNonNull(source);

        // The unchecked cast is completely safe, since at that point
        // we know that the source and target class of the converter
        // match our needs.
        @SuppressWarnings("unchecked")
        Optional<Converter<S, T>> converter = mappings.get(nonNullSource.getClass()).stream()
                .filter(c -> c.getTargetClass().equals(Objects.requireNonNull(targetClass)))
                .map(c -> (Converter<S, T>) c)
                .findFirst();

        if (!converter.isPresent()) {
            throw new IllegalArgumentException("No such converter mapping can be found!");
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
            mappings.get(nonNullConverter.getSourceClass()).add(nonNullConverter);
        } else {
            mappings.put(nonNullConverter.getSourceClass(), Collections.singletonList(nonNullConverter));
            System.out.println("");
        }
    }
}
