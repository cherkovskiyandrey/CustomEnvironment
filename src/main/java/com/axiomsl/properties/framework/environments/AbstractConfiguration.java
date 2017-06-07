package com.axiomsl.properties.framework.environments;

import com.axiomsl.properties.framework.ConfigurableConfiguration;
import com.axiomsl.properties.framework.MutablePropertySources;
import com.axiomsl.properties.framework.PropertySourcesPropertyResolver;
import com.axiomsl.properties.framework.convertors.StandardConverter;
import com.axiomsl.properties.framework.mappers.simple.SimpleObjectMapper;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Set;

public abstract class AbstractConfiguration implements ConfigurableConfiguration {

    protected final MutablePropertySources propertySources;
    protected final PropertySourcesPropertyResolver propertyResolver;


    public AbstractConfiguration() {
        this.propertySources = new MutablePropertySources();
        this.propertyResolver = new PropertySourcesPropertyResolver(this.propertySources, ImmutableList.of(new StandardConverter()), new SimpleObjectMapper());
    }

    @Override
    public MutablePropertySources getPropertySources() {
        return this.propertySources;
    }

    @Override
    public boolean containsProperty(String key) {
        return propertyResolver.containsProperty(key);
    }

    @Override
    public String getProperty(String key) {
        return propertyResolver.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return propertyResolver.getProperty(key, defaultValue);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType) {
        return propertyResolver.getProperty(key, targetType);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return propertyResolver.getProperty(key, targetType, defaultValue);
    }

    @Override
    public String getRequiredProperty(String key) throws IllegalStateException {
        return propertyResolver.getRequiredProperty(key);
    }

    @Override
    public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        return propertyResolver.getRequiredProperty(key, targetType);
    }


    @Override
    public <T> T resolvePropertyClass(Class<T> token) throws IllegalStateException {
        return propertyResolver.resolvePropertyClass(token);
    }

    @Override
    public Object getRawProperty(String key, Class targetType, Object defaultValue) {
        return propertyResolver.getRawProperty(key, targetType, defaultValue);
    }

    @Override
    public Set<String> getIndexesByPrefix(String fieldKeyBase) {
        return propertyResolver.getIndexesByPrefix(fieldKeyBase);
    }

}
