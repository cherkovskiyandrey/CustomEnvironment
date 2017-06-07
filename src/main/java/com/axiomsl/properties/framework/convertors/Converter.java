package com.axiomsl.properties.framework.convertors;

@FunctionalInterface
public interface Converter<From, To> {

    To convert(From from) throws Exception;
}
