package com.aigate.aigate_core.config;

import com.aigate.plugin.Plugin;
import com.aigate.plugin.PluginRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PluginConfiguration {

    @Bean
    public PluginRegistry pluginRegistry(List<Plugin> plugins) {
        PluginRegistry registry = new PluginRegistry();
        plugins.forEach(registry::registerPlugin); // auto-register all @Component plugins
        return registry;
    }
}
