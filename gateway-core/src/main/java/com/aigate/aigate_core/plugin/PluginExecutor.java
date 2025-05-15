package com.aigate.aigate_core.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class PluginExecutor {

    private final PluginRegistry pluginRegistry;

    @Autowired
    public PluginExecutor(PluginRegistry registry) {
        this.pluginRegistry = registry;
    }

    public void executePreRequest(PluginContext context) {
        for (Plugin plugin : pluginRegistry.getPlugins()) {
            plugin.preRequest(context);
        }
    }

    public void executePostRequest(PluginContext context) {
        for (Plugin plugin : pluginRegistry.getPlugins()) {
            plugin.preResponse(context);
        }
    }

    public void executeOnError(Throwable error, PluginContext context) {
        for (Plugin plugin : pluginRegistry.getPlugins()) {
            plugin.onError(error, context);
        }
    }
}