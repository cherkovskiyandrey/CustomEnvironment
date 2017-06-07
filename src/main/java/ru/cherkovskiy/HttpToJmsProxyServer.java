package ru.cherkovskiy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import ru.cherkovskiy.configuration.AppConfig;
import ru.cherkovskiy.configuration.RatpackExtraProperties;

@SpringBootApplication
public class HttpToJmsProxyServer {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(AppConfig.class);
        ctx.getEnvironment().getPropertySources().addLast(new SimpleCommandLinePropertySource(args));
        ctx.refresh();

        RatpackExtraProperties ibmmqProperties  = ctx.getBean(RatpackExtraProperties.class);

        System.out.println(ibmmqProperties);
    }
}

