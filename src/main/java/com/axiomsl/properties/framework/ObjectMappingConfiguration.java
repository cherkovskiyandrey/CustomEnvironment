package com.axiomsl.properties.framework;


import com.axiomsl.properties.framework.annotations.ConfigurationProperties;
import com.axiomsl.properties.framework.annotations.ConfigurationValue;
import com.axiomsl.properties.framework.annotations.NestedConfigurationProperties;

public interface ObjectMappingConfiguration extends Configuration {


    /**
     * Method try to build object by token class.
     * Use annotations:
     * <ol>
     *     <li>{@link ConfigurationProperties}</li>
     *     <li>{@link NestedConfigurationProperties}</li>
     *     <li>{@link ConfigurationValue}</li>
     * </ol>
     *
     * TODO:describe how to use
     *
     * @param token
     * @param <T>
     * @return
     * @throws IllegalStateException
     */
    <T> T resolvePropertyClass(Class<T> token) throws IllegalStateException;

}
