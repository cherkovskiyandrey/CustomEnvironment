package com.axiomsl.properties.framework.environments;

import com.axiomsl.properties.framework.sources.PropertySource;
import com.axiomsl.properties.framework.sources.SystemEnvironmentPropertySource;

import java.util.Set;

public class StandardConfiguration extends AbstractConfiguration {

    /**
     * System CONFIGURATION property source name: {@value}
     */
    public static final String SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME = "systemEnvironment";

    /**
     * JVM system properties property source name: {@value}
     */
    public static final String SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME = "systemProperties";

    public StandardConfiguration() {
        propertySources.addLast(new PropertySource(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME) {
            @Override
            public Object getProperty(String name) {
                return System.getProperty(name);
            }

            @Override
            public Set<String> getAllPropertiesKeys() {
                return System.getProperties().stringPropertyNames();
            }
        });
        propertySources.addLast(new SystemEnvironmentPropertySource(SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME));
    }
}