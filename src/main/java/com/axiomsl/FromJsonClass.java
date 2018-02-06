package com.axiomsl;

public class FromJsonClass {

    private final String fromHolder;

    public FromJsonClass(String fromHolder) {
        this.fromHolder = fromHolder;
    }

    @Override
    public String toString() {
        return "FromJsonClass{" +
                "fromHolder='" + fromHolder + '\'' +
                '}';
    }
}
