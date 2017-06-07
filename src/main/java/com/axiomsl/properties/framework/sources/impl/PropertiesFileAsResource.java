package com.axiomsl.properties.framework.sources.impl;

import com.axiomsl.properties.framework.sources.MapPropertySource;
import com.axiomsl.properties.framework.utils.CommonUtils;

import java.io.InputStream;

public class PropertiesFileAsResource extends MapPropertySource {

    public PropertiesFileAsResource(String name, ClassLoader classLoader, String filename) {
        super(name, CommonUtils.readFile(() -> {
            final InputStream resource = classLoader.getResourceAsStream(filename);
            if(resource == null) {
                throw new IllegalArgumentException(filename);
            }
            return resource;
        }));
    }
}
