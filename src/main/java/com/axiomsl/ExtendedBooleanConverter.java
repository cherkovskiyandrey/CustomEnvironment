package com.axiomsl;

import com.axiomsl.properties.framework.convertors.Converter;

import java.util.stream.Stream;

public class ExtendedBooleanConverter implements Converter<String, Boolean> {

    @Override
    public Boolean convert(String s) throws Exception {
        return Stream.of("TRUE", "ON", "YES", "1").anyMatch(s::equalsIgnoreCase) ? Boolean.TRUE :
                Stream.of("FALSE", "OFF", "NO", "0").anyMatch(s::equalsIgnoreCase) ? Boolean.FALSE : null;
    }
}

