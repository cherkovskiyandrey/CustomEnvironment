package com.axiomsl;

import com.axiomsl.properties.framework.annotations.ConfigurationProperties;
import com.axiomsl.properties.framework.annotations.ConfigurationValue;

import java.net.InetAddress;


public class ImmutableProperties {

    private final InetAddress addr;
    private final String description;
    private final String composableProp;
    private String composableProp1;

    private ImmutableProperties(InetAddress addr, String description, String composableProp) {
        this.addr = addr;
        this.description = description;
        this.composableProp  = composableProp != null ? composableProp : addr.getCanonicalHostName() + description;
    }

    @ConfigurationProperties("some.properties")
    public static class Builder {

        @ConfigurationValue(value = "address", defaultValue = "127.0.0.1")
        private InetAddress addr;

        @ConfigurationValue(value = "description", defaultValue = "Default description.")
        private String description;

        @ConfigurationValue(value = "compos")
        private String composableProp;

        @ConfigurationValue(value = "compos")
        private String composableProp1;

        public ImmutableProperties build() {
            return new ImmutableProperties(addr, description, composableProp);
        }
    }

    @Override
    public String toString() {
        return "ImmutableProperties{" +
                "addr=" + addr +
                ", description='" + description + '\'' +
                ", composableProp='" + composableProp + '\'' +
                ", composableProp1='" + composableProp1 + '\'' +
                '}';
    }
}
