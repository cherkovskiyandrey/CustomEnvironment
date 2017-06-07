package com.axiomsl.properties.framework.convertors;

public interface ConverterService {

    <From, To> boolean canConvert(Class<From> from, Class<To> toCls);

    <From, To> To convert(From fromCls, Class<To> toCls) throws IllegalStateException;

}
