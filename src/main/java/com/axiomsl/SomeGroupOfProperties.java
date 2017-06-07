package com.axiomsl;


import com.axiomsl.properties.framework.annotations.ConfigurationProperties;
import com.axiomsl.properties.framework.annotations.ConfigurationValue;
import com.google.common.collect.Lists;

import java.util.List;

@ConfigurationProperties("some.properties")
public class SomeGroupOfProperties {

    @ConfigurationValue(value = "bool", defaultValue = "false")
    private Boolean boolObjProp;

    @ConfigurationValue("byte")
    private Byte byteObjProp;

    @ConfigurationValue("short")
    private Short shortObjProp;

    @ConfigurationValue("int")
    private Integer intObjProp;

    @ConfigurationValue("long")
    private Long longObjProp;

    @ConfigurationValue("float")
    private Float floatObjProp;

    @ConfigurationValue("double")
    private Double doubleObjProp;

    @ConfigurationValue(value = "bool2", defaultValue = "true")
    private boolean boolProp;

    @ConfigurationValue("byte")
    private byte byteProp;

    @ConfigurationValue("char")
    private char charProp;

    @ConfigurationValue("short")
    private short shortProp;

    @ConfigurationValue("int")
    private int intProp;

    @ConfigurationValue("long")
    private long longProp;

    @ConfigurationValue("float")
    private float floatProp;

    @ConfigurationValue(value = "double")
    private double doubleProp;

    @ConfigurationValue(value = "string", defaultValue = "localhost")
    private String stringProp;

    /**
     * Something like this:
     *
     * some.properties.listSubGroupOfProp[0].string = 123;
     * some.properties.listSubGroupOfProp[1].string = 123;
     * some.properties.listSubGroupOfProp[2].string = 123;
     * ...
     *
     */
    @ConfigurationValue(value = "listSubGroupOfProp")
    private List<SomeSubGroupOfProps> someSubGroupOfPropsList = Lists.newArrayList();


    /**
     * Something like this:
     *
     * some.properties.listStrings[0] = 123;
     * some.properties.listStrings[1] = 123;
     * some.properties.listStrings[2] = 123;
     * ...
     *
     */
    @ConfigurationValue(value = "listStrings")
    private List<String> strings = Lists.newArrayList();


    @ConfigurationValue(value = "subGroupOfProp")
    private SomeSubGroupOfProps someSubGroupOfProps;


    @Override
    public String toString() {
        return "SomeGroupOfProperties{" +
                "boolObjProp=" + boolObjProp +
                ", byteObjProp=" + byteObjProp +
                ", shortObjProp=" + shortObjProp +
                ", intObjProp=" + intObjProp +
                ", longObjProp=" + longObjProp +
                ", floatObjProp=" + floatObjProp +
                ", doubleObjProp=" + doubleObjProp +
                ", boolProp=" + boolProp +
                ", byteProp=" + byteProp +
                ", charProp=" + charProp +
                ", shortProp=" + shortProp +
                ", intProp=" + intProp +
                ", longProp=" + longProp +
                ", floatProp=" + floatProp +
                ", doubleProp=" + doubleProp +
                ", stringProp='" + stringProp + '\'' +
                ", someSubGroupOfPropsList=" + someSubGroupOfPropsList +
                ", strings=" + strings +
                ", someSubGroupOfProps=" + someSubGroupOfProps +
                '}';
    }
}
