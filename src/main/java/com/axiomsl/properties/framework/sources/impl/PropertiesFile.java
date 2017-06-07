package com.axiomsl.properties.framework.sources.impl;

import com.axiomsl.properties.framework.sources.MapPropertySource;
import com.axiomsl.properties.framework.utils.CommonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PropertiesFile extends MapPropertySource {

    public PropertiesFile(String name, String filename) {
        super(name, CommonUtils.readFile(() -> {
            try {
                return Files.newInputStream(Paths.get(filename));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }));
    }
}
