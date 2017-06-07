package ru.cherkovskiy.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.cherkovskiy.HttpToJmsProxyServer;

@Configuration
@ComponentScan(basePackageClasses = HttpToJmsProxyServer.class)
@EnableConfigurationProperties(RatpackExtraProperties.class)
public class AppConfig {

    @Bean("TTT")
    public String voo(RatpackExtraProperties ratpackExtraProperties) {
        return ratpackExtraProperties.toString();
    }
}
