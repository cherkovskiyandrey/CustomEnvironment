package com.axiomsl.properties.framework.convertors;

import com.axiomsl.properties.framework.resources.NamedResource;

public interface ConverterService extends NamedResource {

    <From, To> boolean canConvert(Class<From> from, Class<To> toCls);

    <From, To> To convert(From fromCls, Class<To> toCls) throws IllegalStateException;

}
