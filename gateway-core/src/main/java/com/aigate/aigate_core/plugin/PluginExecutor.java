package com.aigate.aigate_core.plugin;

import com.aigate.aigate_core.models.ReqResContext;
import com.aigate.aigate_core.interfaces.Plugin;
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
        for (Plugin plugin : pluginRegistry.getPlugins()) {
            plugin.request(context);
        }
    }

    public void executePostRequest(ReqResContext context) {
        for (Plugin plugin : pluginRegistry.getPlugins()) {
            plugin.response(context);
        }
    }

    public void executeOnError(Throwable error, ReqResContext context) {
        for (Plugin plugin : pluginRegistry.getPlugins()) {
            plugin.onError(error, context);
        }
    }
}