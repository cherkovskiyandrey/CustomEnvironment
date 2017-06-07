package com.axiomsl;

public class PropX<T> {
    private final String key;
    private final Object rawValue;
    private final T value;

    public PropX(String key, Class<T> token) {
        this.key = key;
        this.rawValue = System.getProperty(key);
        this.value = converter(rawValue, token);
    }

    @SuppressWarnings("unchecked")
    private static <U> U converter(Object rawValue, Class<U> token) {
        return (U) converterRaw(rawValue, token);
    }

    private static Object converterRaw(Object raw, Class token) {
        if (raw == null) {
            if (token.isPrimitive()) {
                if (boolean.class.equals(token)) {
                    return false;
                }
                if (byte.class.equals(token)) {
                    return (byte) 0;
                }
                if (char.class.equals(token)) {
                    return "";
                }
                if (short.class.equals(token)) {
                    return (short) 0;
                }
                if (int.class.equals(token)) {
                    return 0;
                }
                if (long.class.equals(token)) {
                    return 0L;
                }
                if (float.class.equals(token)) {
                    return 0.;
                }
                return 0.D;
            } else {
                return null;
            }
        }

        //todo
        return null;
    }

    public String getKey() {
        return key;
    }

    public Object getRawValue() {
        return rawValue;
    }

    public T getValue() {
        return value;
    }
}
