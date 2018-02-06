package com.axiomsl.properties.framework;

import com.axiomsl.properties.framework.convertors.ConverterService;
import com.axiomsl.properties.framework.mappers.ObjectMapper;
import com.axiomsl.properties.framework.sources.PropertySource;
import com.axiomsl.properties.framework.utils.CommonUtils;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;


public class PropertySourcesPropertyResolver implements ObjectMappingConfiguration {
    private final static Pattern pattern = Pattern.compile("^\\[([^\\[\\]]+)].*$");
    private final Iterable<PropertySource<?>> propertySources;
    private final Iterable<ConverterService> converterServices;
    private final ObjectMapper objectMapper;


    /**
     * Create a new resolver against the given property sources.
     *
     * @param propertySources  the set of {@link PropertySource} objects to use
     * @param converterService
     * @param objectMapper
     */
    public PropertySourcesPropertyResolver(Iterable<PropertySource<?>> propertySources,
                                           Iterable<ConverterService> converterService,
                                           ObjectMapper objectMapper) {
        this.propertySources = propertySources;
        this.converterServices = converterService;
        this.objectMapper = objectMapper;
    }


    @Override
    public boolean containsProperty(String key) {
        if (this.propertySources != null) {
            for (PropertySource<?> propertySource : this.propertySources) {
                if (propertySource.containsProperty(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getProperty(String key) {
        return getProperty(key, String.class);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return containsProperty(key) ? getProperty(key) : defaultValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProperty(String key, Class<T> targetValueType) {
        return propertySourcesAsStream()
                .filter(p -> p.containsProperty(key))
                .map(p -> p.getProperty(key))
                .map(o -> tryConvert(o, targetValueType))
                .findFirst()
                .orElse((T) CommonUtils.getDefaultValueFor(targetValueType))
                ;
    }

    private <From, To> To tryConvert(From from, Class<To> targetValueType) {
        return converterServicesAsStream()
                .filter(conv -> conv.canConvert(from.getClass(), targetValueType))
                .map(conv -> conv.convert(from, targetValueType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Can`t convert from %s to %s", from.getClass().getSimpleName(), targetValueType.getSimpleName())))
                ;
    }


    @Override
    public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return containsProperty(key) ? getProperty(key, targetType) : defaultValue;
    }

    @Override
    public String getRequiredProperty(String key) throws IllegalStateException {
        final String prop = getProperty(key);
        if (prop == null) {
            throw new IllegalStateException(key);
        }
        return prop;
    }

    @Override
    public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        final T prop = getProperty(key, targetType);
        if (prop == null) {
            throw new IllegalStateException(format("Could not find mandatory property %s", key));
        }
        return prop;
    }

    @Override
    public Set<String> getIndexesByPrefix(String fieldKeyBase) {
        return propertySourcesAsStream()
                .map(PropertySource::getAllPropertiesKeys)
                .map(l -> getIndexesByPrefixHelper(l, fieldKeyBase))
                .filter(l -> !l.isEmpty())
                .findFirst()
                .orElse(Collections.emptySet());
    }

    private static Set<String> getIndexesByPrefixHelper(Set<String> source, String fieldKeyBase) {
        return source.stream()
                .filter(el -> el.startsWith(fieldKeyBase))
                .map(el -> el.substring(fieldKeyBase.length()))
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .map(m -> m.replaceFirst("$1"))
                .collect(Collectors.toSet());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getPropertyWithRawDefault(String key, Class<T> targetType, Object defaultValue) {
        return propertySourcesAsStream()
                .filter(p -> p.containsProperty(key))
                .map(p -> p.getProperty(key))
                .map(o -> tryConvert(o, targetType))
                .findFirst()
                .orElseGet(() -> defaultValue != null ? tryConvert(defaultValue, targetType) : null);
    }

    @Override
    public Object getRawProperty(String key, String defaultValue) {
        return propertySourcesAsStream()
                .filter(p -> p.containsProperty(key))
                .map(p -> p.getProperty(key))
                .findFirst()
                .orElse(defaultValue);
    }

    @Override
    public Object getRawRequiredProperty(String key) throws IllegalStateException {
        return propertySourcesAsStream()
                .filter(p -> p.containsProperty(key))
                .map(p -> p.getProperty(key))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Could not find mandatory property %s", key)))
                ;
    }

    private Stream<PropertySource<?>> propertySourcesAsStream() {
        return StreamSupport.stream(propertySources.spliterator(), false);
    }

    private Stream<ConverterService> converterServicesAsStream() {
        return StreamSupport.stream(converterServices.spliterator(), false);
    }

    @Override
    public <T> T resolvePropertyClass(Class<T> token) throws IllegalStateException {
        return objectMapper.readValue(token, this);
    }

}