package com.axiomsl.properties.framework.convertors;

import com.google.common.collect.ImmutableTable;

import java.net.InetAddress;

import static java.lang.String.format;


public class StandardConverter implements ConverterService {

    private static <To> Converter<String, To> stringPrepareHelper(Converter<String, To> func) {
        return str -> func.convert(str.trim());
    }

    /**
     * Row - from type, Column - to type.
     */
    private final static ImmutableTable<Class, Class, Converter> converters = new ImmutableTable.Builder<Class, Class, Converter>()

            //bool
            .put(String.class, boolean.class, stringPrepareHelper(Boolean::parseBoolean))
            .put(String.class, Boolean.class, stringPrepareHelper(Boolean::parseBoolean))

            //byte
            .put(String.class, byte.class, stringPrepareHelper(Byte::parseByte))
            .put(String.class, Byte.class, stringPrepareHelper(Byte::parseByte))

            //short
            .put(String.class, short.class, stringPrepareHelper(Short::parseShort))
            .put(String.class, Short.class, stringPrepareHelper(Short::parseShort))

            //int
            .put(String.class, int.class, stringPrepareHelper(Integer::parseInt))
            .put(String.class, Integer.class, stringPrepareHelper(Integer::parseInt))

            //long
            .put(String.class, long.class, stringPrepareHelper(Long::parseLong))
            .put(String.class, Long.class, stringPrepareHelper(Long::parseLong))

            //float
            .put(String.class, float.class, stringPrepareHelper(Float::parseFloat))
            .put(String.class, Float.class, stringPrepareHelper(Float::parseFloat))

            //double
            .put(String.class, double.class, stringPrepareHelper(Double::parseDouble))
            .put(String.class, Double.class, stringPrepareHelper(Double::parseDouble))

            //InetAddress
            .put(String.class, InetAddress.class, stringPrepareHelper(InetAddress::getByName))

            //TODO: expand if necessary for basic types

            .build();

    @Override
    @SuppressWarnings("unchecked")
    public <From, To> boolean canConvert(Class<From> fromCls, Class<To> toCls) {
        return toCls.isAssignableFrom(fromCls) ||
                converters.get(fromCls, toCls) != null;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <From, To> To convert(From from, Class<To> toCls) throws IllegalStateException {
        final Class<From> fromCls = (Class<From>) from.getClass();
        if (toCls.isAssignableFrom(fromCls)) {
            return (To) from;
        }

        final Converter<From, To> converter = converters.get(fromCls, toCls);
        if (converter == null) {
            throw new IllegalStateException(format("Can`t convert from %s to %s", fromCls.getSimpleName(), toCls.getSimpleName()));
        }

        try {
            return converter.convert(from);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
