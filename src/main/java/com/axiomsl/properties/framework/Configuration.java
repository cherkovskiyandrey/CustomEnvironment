package com.axiomsl.properties.framework;

import java.util.List;
import java.util.Set;

/**
 * Interface for resolving properties against any underlying source.
 *
 * @author Cherkovskiy Andrey
 */
public interface Configuration {

    /**
     * Return whether the given property key is available for resolution,
     * i.e. if the value for the given key is not {@code null}.
     */
    boolean containsProperty(String key);

    /**
     * Return the property value associated with the given key,
     * or {@code null} if the key cannot be resolved.
     *
     * @param key the property name to resolve
     */
    String getProperty(String key);

    /**
     * Return the property value associated with the given key, or
     * {@code defaultValue} if the key cannot be resolved.
     *
     * @param key          the property name to resolve
     * @param defaultValue the default value to return if no value is found
     */
    String getProperty(String key, String defaultValue);


    /**
     * Return the property value associated with the given key (never {@code null}).
     *
     * @throws IllegalStateException if the key cannot be resolved
     */
    String getRequiredProperty(String key) throws IllegalStateException;


    /**
     * Return the property value associated with the given key,
     * or {@code null} if the key cannot be resolved.
     *
     * @param key        the property name to resolve
     * @param targetType the expected type of the property value
     */
    <T> T getProperty(String key, Class<T> targetType);


    /**
     * Return the property value associated with the given key,
     * or {@code defaultValue} if the key cannot be resolved.
     *
     * @param key          the property name to resolve
     * @param targetType   the expected type of the property value
     * @param defaultValue the default value to return if no value is found
     */
    <T> T getProperty(String key, Class<T> targetType, T defaultValue);


    /**
     * Return the property value associated with the given key, converted to the given
     * targetType (never {@code null}).
     *
     * @throws IllegalStateException if the given key cannot be resolved
     */
    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;


    /**
     * Return the raw property value associated with the given key,
     * or {@code defaultValue} if the key cannot be resolved.
     *
     * @param key          the property name to resolve
     * @param targetType   the expected type of the property value
     * @param defaultValue the raw default value to convert and to return if no value is found
     */
    Object getRawProperty(String key, Class<?> targetType, Object defaultValue) throws IllegalStateException;


    /**
     * Return all indexes for list of elements by prefix.
     * <p>
     * Example:
     * <p>
     * {@code some.properties.listStrings[default] = 0;} <br>
     * {@code some.properties.listStrings[0] = 1;} <br>
     * {@code some.properties.listStrings[10] = 2;} <br>
     * {@code some.properties.listStrings[20] = 3;} <br>
     * <p>
     * <br>
     * return list of next elements: default, 0, 10, 20
     *
     * @param fieldKeyBase
     * @return
     */
    Set<String> getIndexesByPrefix(String fieldKeyBase);
}
