package com.aigate.aigate_core.config;

import com.aigate.aigate_core.interfaces.CorePlugin;
import com.aigate.aigate_core.plugin.PluginRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PluginConfiguration {

    @Bean
    public PluginRegistry pluginRegistry(List<CorePlugin> plugins) {
        PluginRegistry registry = new PluginRegistry();
        plugins.forEach(registry::registerPlugin); // auto-register all @Component plugins
        return registry;
    }
}
