package com.axiomsl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ViaJackson {

    public ViaJackson(@JsonProperty(value = "xxxxx", defaultValue = "-125") int xxxxx) {
        this.xxxxx = xxxxx;
    }

    public static class Enclosed {
        @JsonProperty(value = "string", defaultValue = "---")
        private String string;

        @JsonProperty("integer")
        private int integer;

        @Override
        public String toString() {
            return "Enclosed{" +
                    "string='" + string + '\'' +
                    ", integer=" + integer +
                    '}';
        }
    }

    @JsonProperty("string")
    private String string;

    @JsonProperty("integer")
    private int integer;

    private final int xxxxx;

    @JsonProperty("enclosedList")
    private List<Enclosed> enclosedList;

    @JsonProperty("enclosed")
    private Enclosed enclosed;


    @Override
    public String toString() {
        return "ViaJackson{" +
                "string='" + string + '\'' +
                ", integer=" + integer +
                ", xxxxx=" + xxxxx +
                ", enclosedList=" + enclosedList +
                ", enclosed=" + enclosed +
                '}';
    }

    public static void main(String[] args) throws IOException {

        JavaPropsMapper javaPropsMapper = new JavaPropsMapper();
        Properties properties = new Properties();
        properties.setProperty("string", "Hello world!");
        properties.setProperty("integer", "123");
        properties.setProperty("enclosedList[1].string", "Lala1");
        properties.setProperty("enclosedList[1].integer", "1");
        properties.setProperty("enclosedList[2].string", "Lala2");
        properties.setProperty("enclosedList[2].integer", "2");

        ViaJackson viaJackson = javaPropsMapper.readValue(properties, ViaJackson.class);
        System.out.println(viaJackson);

        Properties propertiesOutput = new Properties();
        javaPropsMapper.writeValue(propertiesOutput, viaJackson);

        System.out.println(propertiesOutput);
    }

}
