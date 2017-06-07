package com.axiomsl;

import com.axiomsl.properties.framework.annotations.ConfigurationProperties;
import com.axiomsl.properties.framework.annotations.ConfigurationValue;

import java.util.List;

@ConfigurationProperties("some.properties")
public class ImmutableProperties2 {
    private final short shortObjProp;
    private final String stringProp;
    private final List<SomeSubGroupOfProps> someSubGroupOfProps;
    private final List<String> strings;
    private final int valueWithoutAnnotation;
    private final String stringProp2;
    private final SomeSubGroupOfProps someSubGroupOfPropsSingle;
    @ConfigurationValue(value = "mutableProperty", defaultValue = "dddddd")
    private String mutableProperty;

    public ImmutableProperties2(@ConfigurationValue("short") short shortObjProp,
                                @ConfigurationValue(value = "string", defaultValue = "127.0.0.1") String stringProp,
                                @ConfigurationValue(value = "listSubGroupOfProp", required = true) List<SomeSubGroupOfProps> someSubGroupOfProps,
                                @ConfigurationValue(value = "listStrings") List<String> strings,
                                @ConfigurationValue(value = "string2") String stringProp2,
                                int valueWithoutAnnotation,
                                @ConfigurationValue(value = "listSubGroupOfProp.single") SomeSubGroupOfProps someSubGroupOfPropsSingle) {
        this.shortObjProp = shortObjProp;
        this.stringProp = stringProp;
        this.someSubGroupOfProps = someSubGroupOfProps;
        this.strings = strings;
        this.valueWithoutAnnotation = valueWithoutAnnotation;
        this.stringProp2 = stringProp2;
        this.someSubGroupOfPropsSingle = someSubGroupOfPropsSingle;
    }

    public Short getShortObjProp() {
        return shortObjProp;
    }

    public String getStringProp() {
        return stringProp;
    }

    public List<SomeSubGroupOfProps> getSomeSubGroupOfProps() {
        return someSubGroupOfProps;
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public String toString() {
        return "ImmutableProperties2{" +
                "shortObjProp=" + shortObjProp +
                ", stringProp='" + stringProp + '\'' +
                ", someSubGroupOfProps=" + someSubGroupOfProps +
                ", strings=" + strings +
                ", valueWithoutAnnotation=" + valueWithoutAnnotation +
                ", stringProp2='" + stringProp2 + '\'' +
                ", someSubGroupOfPropsSingle=" + someSubGroupOfPropsSingle +
                ", mutableProperty='" + mutableProperty + '\'' +
                '}';
    }
}
