package com.axiomsl.properties.framework.mappers.simple;

import com.axiomsl.properties.framework.Configuration;
import com.axiomsl.properties.framework.annotations.ConfigurationProperties;
import com.axiomsl.properties.framework.annotations.ConfigurationValue;
import com.axiomsl.properties.framework.annotations.SubConfigurationProperties;
import com.axiomsl.properties.framework.mappers.ObjectMapper;
import com.axiomsl.properties.framework.utils.CommonUtils;
import com.google.common.base.Strings;

import java.util.*;

import static java.lang.String.format;


public class SimpleObjectMapper implements ObjectMapper {

    @Override
    public <T> T readValue(Class<T> token, Configuration configuration) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(configuration);

        final ConfigurationProperties configurationProperties = token.getAnnotation(ConfigurationProperties.class);
        if (configurationProperties == null) {
            throw new IllegalStateException(format("Class %s does not have ConfigurationProperties annotation.", token.getSimpleName()));
        }

        final String prefix = configurationProperties.value();
        return readValueWithPrefix(token, prefix, configuration).build();
    }

    private <T> ObjectBuilder<T> readValueWithPrefix(Class<T> token, String prefix, Configuration configuration) {
        final ObjectBuilder<T> objectBuilder = ObjectBuilder.of(token);

        for (ObjectElement<?> objectElement : objectBuilder) {
            final ConfigurationValue configurationValue = objectElement.getAnnotation(ConfigurationValue.class);

            if (configurationValue != null) {
                if (isCollection(objectElement)) {
                    initCollection(objectElement, prefix, configurationValue, configuration);
                } else {
                    initSimpleValue(objectElement, prefix, configurationValue, configuration);
                }
            }
        }
        return objectBuilder;
    }

    private boolean isCollection(ObjectElement<?> objectElement) {
        return objectElement.getAsParameterizedType().isPresent() && Collection.class.isAssignableFrom(objectElement.getType());
    }

    @SuppressWarnings("unchecked")
    private <T> void initSimpleValue(ObjectElement<T> objectElement, String prefix, ConfigurationValue configurationValue, Configuration configuration) {
        final String fieldKey = CommonUtils.lazyJoin(".", prefix, configurationValue.value());
        final boolean isRequired = configurationValue.required();
        final String defaultValue = configurationValue.defaultValue();
        final Class<T> fieldType = objectElement.getType();
        final boolean isEnclosed = fieldType.getAnnotation(SubConfigurationProperties.class) != null;

        Object fieldValue;
        if (isRequired) {
            if (isEnclosed) {
                fieldValue = readValueWithPrefix(fieldType, fieldKey, configuration).buildIfNotEmpty();
                if (fieldValue == null) {
                    throw new IllegalStateException(format("Could not find mandatory property %s", fieldKey));
                }
            } else {
                fieldValue = configuration.getRequiredProperty(fieldKey, fieldType);
            }
        } else {
            if (isEnclosed) {
                fieldValue = readValueWithPrefix(fieldType, fieldKey, configuration).buildIfNotEmpty();
            } else {
                fieldValue = configuration.getRawProperty(fieldKey, fieldType, Strings.isNullOrEmpty(defaultValue) ? null : defaultValue);
            }
        }

        if (fieldValue != null) {
            objectElement.setValue((T) fieldValue);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void initCollection(ObjectElement<T> objectElement, String prefix, ConfigurationValue configurationValue, Configuration configuration) {
        final Collection<Object> collection = new ArrayList<>();
        final boolean isRequired = configurationValue.required();
        final Class<?> collectionType = (Class<?>)objectElement.getAsParameterizedType().get().getActualTypeArguments()[0];
        final String fieldKeyBase = CommonUtils.lazyJoin(".", prefix, configurationValue.value());
        final boolean isEnclosed = collectionType.getAnnotation(SubConfigurationProperties.class) != null;
        final Set<String> allIndexes = configuration.getIndexesByPrefix(fieldKeyBase);

        allIndexes.forEach(idx -> {
            final String fieldKey = fieldKeyBase + "[" + idx + "]";
            final Object element = isEnclosed ? readValueWithPrefix(collectionType, fieldKey, configuration).buildIfNotEmpty() :
                    configuration.getProperty(fieldKey, collectionType);

            if (element != null) {
                collection.add(element);
            }
        });

        if (!collection.isEmpty()) {
            objectElement.setValue((T) collection);
        } else if (isRequired) {
            throw new IllegalStateException(format("Could not find mandatory properties list %s", fieldKeyBase));
        }
    }

}
