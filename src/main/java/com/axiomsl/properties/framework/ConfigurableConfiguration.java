package com.axiomsl.properties.framework;

import com.axiomsl.properties.framework.convertors.ConverterService;
import com.axiomsl.properties.framework.resources.MutableResources;
import com.axiomsl.properties.framework.sources.PropertySource;

public interface ConfigurableConfiguration extends ObjectMappingConfiguration {

    MutableResources<PropertySource<?>> getPropertySources();

    MutableResources<ConverterService> getConverterServices();
}

