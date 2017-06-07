package com.axiomsl.properties.framework.mappers.simple;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

interface ObjectElement<T> {

    <U extends Annotation> U getAnnotation(Class<U> annotationType);

    Class<T> getType();

    Optional<ParameterizedType> getAsParameterizedType();

    void setValue(T value);
}
