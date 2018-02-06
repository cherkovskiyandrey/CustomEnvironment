package com.axiomsl;

import com.axiomsl.properties.framework.ConfigurableConfiguration;
import com.axiomsl.properties.framework.Configuration;
import com.axiomsl.properties.framework.convertors.Converter;
import com.axiomsl.properties.framework.convertors.ConverterServiceImpl;
import com.axiomsl.properties.framework.environments.StandardConfiguration;
import com.axiomsl.properties.framework.sources.impl.PropertiesFile;
import com.axiomsl.properties.framework.sources.impl.PropertiesFileAsResource;
import com.google.common.collect.ImmutableTable;

import java.util.function.Function;


public class Main {

    public final static Configuration CONFIGURATION = new StandardConfiguration();


    static class A {
        int i;

        A(int i) {
            this.i = i;
        }
    }

    ;

    private final static Function<String, String> stringPrepare = String::trim;

    public static void main(String[] args) throws Exception {
        {
            System.setProperty("some.test", "1.123");
            final Configuration configuration = new StandardConfiguration();
            double d = configuration.getProperty("some.test", Double.class, 1.);
            System.out.println(d);

            System.out.println(configuration.getProperty("some.testxsxsx", boolean.class));
        }
        //-------------------------
        {
            final ConfigurableConfiguration configurableEnvironment = new StandardConfiguration();
            configurableEnvironment.getPropertySources().addLast(new PropertiesFile("serverProperties", "program.prop"));

            Object d = configurableEnvironment.getPropertyWithRawDefault("some.properties.bool", boolean.class, "false");
            System.out.println(d);
        }
        //-------------------------
        {
            System.setProperty("some.properties.listStrings[0]", "REWRITED!!!!"); // todo
            System.setProperty("some.properties.float", "999.1423");
            final ConfigurableConfiguration configurableEnvironment = new StandardConfiguration();
            configurableEnvironment.getPropertySources().addLast(new PropertiesFile("serverProperties", "program.prop"));
            configurableEnvironment.getPropertySources().addLast(new PropertiesFileAsResource("serverProperties", Main.class.getClassLoader(), "program.prop"));

            final SomeGroupOfProperties someGroupOfProperties = configurableEnvironment.resolvePropertyClass(SomeGroupOfProperties.class);
            System.out.println(someGroupOfProperties.toString());
        }
        //-------------------------
        {
            final ConfigurableConfiguration configurableEnvironment = new StandardConfiguration();
            configurableEnvironment.getPropertySources().addLast(new PropertiesFile("serverProperties", "C:\\Andrey\\TestApp_Java\\CustomEnvironment\\program.prop"));

            final ImmutableProperties immutableProperties = configurableEnvironment.resolvePropertyClass(ImmutableProperties.Builder.class).build();

            System.out.println(immutableProperties.toString());
        }
        //-------------------------
        {
            final ConfigurableConfiguration configurableEnvironment = new StandardConfiguration();
            configurableEnvironment.getPropertySources().addLast(new PropertiesFile("serverProperties", "C:\\Andrey\\TestApp_Java\\CustomEnvironment\\program.prop"));

            final ImmutableProperties2 immutableProperties = configurableEnvironment.resolvePropertyClass(ImmutableProperties2.class);

            System.out.println(immutableProperties.toString());
        }
        //-------------------------
        {
            System.out.println(PropertiesX.INSTANCE.getAxiomApplicationDir().getValue());
            System.out.println(PropertiesX.INSTANCE.getAxiomApplicationDir().getKey() + " -> " + PropertiesX.INSTANCE.getAxiomApplicationDir().getValue());
        }
        //Global custom convectors
        //-------------------------
        {
            final ConfigurableConfiguration configurableEnvironment = new StandardConfiguration();
            configurableEnvironment.getPropertySources().addLast(new PropertiesFile("serverProperties", "program.prop"));

            ImmutableTable<Class, Class, Converter> extendedBooleanConverters = new ImmutableTable.Builder<Class, Class, Converter>()
                    .put(String.class, boolean.class, new ExtendedBooleanConverter())
                    .put(String.class, Boolean.class, new ExtendedBooleanConverter())
                    .build();

            configurableEnvironment.getConverterServices().addFirst(new ConverterServiceImpl("extraBooleanConverter", extendedBooleanConverters));

            Object d = configurableEnvironment.getPropertyWithRawDefault("some.properties.bool", boolean.class, "false");
            System.out.println(d);
        }
        //Local custom convectors
        //-------------------------
        {
            final ConfigurableConfiguration configurableEnvironment = new StandardConfiguration();
            configurableEnvironment.getPropertySources().addLast(new PropertiesFile("serverProperties", "program.prop"));

            final SomeGroupOfProperties someGroupOfProperties = configurableEnvironment.resolvePropertyClass(SomeGroupOfProperties.class);
            System.out.println(someGroupOfProperties.toString());
        }
    }
}
