package io.infectnet.server.service;

/**
 * Interface to be implemented by converters to add converter mappings to the {@link ConverterService}.
 *
 * @param <S> source class
 * @param <T> target class
 */
public interface Converter<S, T> {

    /**
     * Converts an object to the target class.
     *
     * @param source the object to be converted
     * @return the converted object
     */
    T convert(S source);

    /**
     * Returns the source class of the converter.
     *
     * @return the source class
     */
    Class<S> getSourceClass();

    /**
     * Returns the target class of the converter.
     *
     * @return the target class
     */
    Class<T> getTargetClass();

}
