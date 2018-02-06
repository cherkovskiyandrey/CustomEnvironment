package com.axiomsl.properties.framework.annotations;


import com.axiomsl.properties.framework.convertors.Converter;
import com.axiomsl.properties.framework.convertors.EmptyConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationValue {

    String value();

    /**
     * Does not work for collections.
     *
     * @return
     */
    String defaultValue() default "";

    boolean required() default false;

    Class<? extends Converter<?, ?>> converter() default EmptyConverter.class;

}
