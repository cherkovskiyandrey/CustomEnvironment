package com.axiomsl.properties.framework.sources;

import java.util.Map;
import java.util.Set;

public class MapPropertySource extends PropertySource<Map<String, Object>> {

    public MapPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        return this.source.get(name);
    }

    @Override
    public Set<String> getAllPropertiesKeys() {
        return this.source.keySet();
    }

    @Override
    public boolean containsProperty(String name) {
        return this.source.containsKey(name);
    }
}