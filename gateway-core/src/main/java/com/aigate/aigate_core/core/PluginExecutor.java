package com.aigate.aigate_core.core;

import com.aigate.aigate_core.interfaces.CorePlugin;
import com.aigate.aigate_core.Registry.PluginRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PluginExecutor {

    private final PluginRegistry pluginRegistry;

    @Autowired
    public PluginExecutor(PluginRegistry registry) {
        this.pluginRegistry = registry;
    }

    public void executePreRequest(ReqResContext context) {
        for (CorePlugin plugin : pluginRegistry.getPlugins()) {
            plugin.preRequest(context);
        }
    }

    public void executePostRequest(ReqResContext context) {
        for (CorePlugin plugin : pluginRegistry.getPlugins()) {
            plugin.preResponse(context);
        }
    }

    public void executeOnError(Throwable error, ReqResContext context) {
        for (CorePlugin plugin : pluginRegistry.getPlugins()) {
            plugin.onError(error, context);
        }
    }
}