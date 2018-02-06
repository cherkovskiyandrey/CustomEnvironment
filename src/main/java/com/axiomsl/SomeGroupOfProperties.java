package com.axiomsl;


import com.axiomsl.properties.framework.annotations.ConfigurationProperties;
import com.axiomsl.properties.framework.annotations.ConfigurationValue;
import com.axiomsl.properties.framework.convertors.Converter;
import com.google.common.collect.Lists;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

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
     * <p>
     * some.properties.listSubGroupOfProp[0].string = 123;
     * some.properties.listSubGroupOfProp[1].string = 123;
     * some.properties.listSubGroupOfProp[2].string = 123;
     * ...
     */
    @ConfigurationValue(value = "listSubGroupOfProp")
    private List<SomeSubGroupOfProps> someSubGroupOfPropsList;


    /**
     * Something like this:
     * <p>
     * some.properties.listStrings[0] = 123;
     * some.properties.listStrings[1] = 123;
     * some.properties.listStrings[2] = 123;
     * ...
     */
    @ConfigurationValue(value = "listStrings")
    private List<String> strings = Lists.newArrayList();


    @ConfigurationValue(value = "subGroupOfProp")
    private SomeSubGroupOfProps someSubGroupOfProps;

    @ConfigurationValue(value = "propertyWithCustomSerializer", defaultValue = "false")
    private boolean propertyWithCustomSerializer;


    @ConfigurationValue(value = "propertyWithCustomSerializerClass", defaultValue = "{\"a\"=10;\"b\"=\"hello world\"}", converter = JsonCustomDeserializer.class)
    private FromJsonClass propertyWithCustomSerializerClass;


    @ConfigurationValue(value = "propertyWithCustomSerializerLst", defaultValue = "{\"a\"=10;\"b\"=\"hello world\"}", converter = JsonCustomDeserializer.class)
    private Set<FromJsonClass> propertyWithCustomSerializerClassLst;


    @Min(0)
    @Max(Long.MAX_VALUE)
    @ConfigurationValue(value = "longWithBoarders", defaultValue = "10000")
    private long longPropWithBoarders;

    @Min(-10)
    @Max(10)
    @ConfigurationValue(value = "longSetWithRestrictions")
    private SortedSet<Long> longSetWithRestrictions;

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
                ", propertyWithCustomSerializer=" + propertyWithCustomSerializer +
                ", propertyWithCustomSerializerClass=" + propertyWithCustomSerializerClass +
                ", propertyWithCustomSerializerClassLst=" + propertyWithCustomSerializerClassLst +
                ", longPropWithBoarders=" + longPropWithBoarders +
                ", longSetWithRestrictions=" + longSetWithRestrictions +
                '}';
    }

    public static class JsonCustomDeserializer implements Converter<String, FromJsonClass> {
        @Override
        public FromJsonClass convert(String s) throws Exception {
            return new FromJsonClass(s);
        }
    }
}
