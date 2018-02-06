package com.axiomsl.properties.framework.convertors;

public final class EmptyConverter implements Converter<Object, Object> {

    private EmptyConverter() {
    }

    @Override
    public Object convert(Object o) throws Exception {
        throw new UnsupportedOperationException();
    }
}
