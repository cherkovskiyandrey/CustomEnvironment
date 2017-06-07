package com.axiomsl.properties.framework.mappers;

import com.axiomsl.properties.framework.Configuration;


public interface ObjectMapper {

    <T> T readValue(Class<T> token, Configuration configuration);
}
