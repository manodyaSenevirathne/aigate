package com.aigate.aigate_core.config;

import com.aigate.aigate_core.core.RouteRegistry;
import com.aigate.aigate_core.models.RouteConfig;
import com.aigate.aigate_core.models.RouteConfigWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.List;

@Configuration
public class RouteStartupLoader {

    @Bean
    public static RouteRegistry loadFromClasspath() {
        LoaderOptions options = new LoaderOptions();
        Constructor constructor = new Constructor(RouteConfigWrapper.class, options);
        Yaml yaml = new Yaml(constructor);
        InputStream inputStream = RouteStartupLoader.class.getClassLoader()
                .getResourceAsStream("routes.yml");
        if (inputStream == null) {
            throw new RuntimeException("routes.yaml not found in classpath");
        }
        RouteConfigWrapper wrapper = yaml.load(inputStream);

        RouteRegistry registry = new RouteRegistry();

        System.out.println("Registering Route...");
        for (RouteConfig route : wrapper.getRoutes()) {
            System.out.println(route.getId());
            registry.registerRoute(route);
        }

        return registry;
    }

}
