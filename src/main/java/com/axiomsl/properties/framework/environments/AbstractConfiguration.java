package com.axiomsl.properties.framework.environments;

import com.axiomsl.properties.framework.ConfigurableConfiguration;
import com.axiomsl.properties.framework.PropertySourcesPropertyResolver;
import com.axiomsl.properties.framework.convertors.ConverterService;
import com.axiomsl.properties.framework.convertors.StandardConverterService;
import com.axiomsl.properties.framework.mappers.simple.SimpleObjectMapper;
import com.axiomsl.properties.framework.resources.MutableResources;
import com.axiomsl.properties.framework.sources.PropertySource;

import java.util.Set;

public abstract class AbstractConfiguration implements ConfigurableConfiguration {

    protected final MutableResources<PropertySource<?>> propertySources;
    protected final MutableResources<ConverterService> converterServices;
    protected final PropertySourcesPropertyResolver propertyResolver;


    public AbstractConfiguration() {
        this.propertySources = new MutableResources<>();
        this.converterServices = new MutableResources<>();
        this.converterServices.addFirst(new StandardConverterService());
        this.propertyResolver = new PropertySourcesPropertyResolver(this.propertySources, this.converterServices, new SimpleObjectMapper());
    }

    @Override
    public MutableResources<PropertySource<?>> getPropertySources() {
        return propertySources;
    }

    @Override
    public MutableResources<ConverterService> getConverterServices() {
        return converterServices;
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
    public <T> T getPropertyWithRawDefault(String key, Class<T> targetType, Object defaultValue) {
        return propertyResolver.getPropertyWithRawDefault(key, targetType, defaultValue);
    }

    @Override
    public Set<String> getIndexesByPrefix(String fieldKeyBase) {
        return propertyResolver.getIndexesByPrefix(fieldKeyBase);
    }

    @Override
    public Object getRawProperty(String key, String defaultValue) {
        return propertyResolver.getRawProperty(key, defaultValue);
    }

    @Override
    public Object getRawRequiredProperty(String key) throws IllegalStateException {
        return propertyResolver.getRawRequiredProperty(key);
    }
}
