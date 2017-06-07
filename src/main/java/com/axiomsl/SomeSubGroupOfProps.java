package com.axiomsl;


import com.axiomsl.properties.framework.annotations.ConfigurationValue;
import com.axiomsl.properties.framework.annotations.SubConfigurationProperties;

import java.net.InetAddress;

@SubConfigurationProperties
public class SomeSubGroupOfProps {

    @ConfigurationValue(value = "", defaultValue = "---")
    private String defaultValue;

    @ConfigurationValue("string")
    private String stringProp;

    @ConfigurationValue("inetAddress")
    private InetAddress inetAddress;

    public String getStringProp() {
        return stringProp;
    }

    @Override
    public String toString() {
        return "SomeSubGroupOfProps{" +
                "defaultValue='" + defaultValue + '\'' +
                ", stringProp='" + stringProp + '\'' +
                ", inetAddress=" + inetAddress +
                '}';
    }
}
