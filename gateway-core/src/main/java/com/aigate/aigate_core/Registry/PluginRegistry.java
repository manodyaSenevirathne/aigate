package com.aigate.aigate_core.Registry;

import com.aigate.aigate_core.interfaces.CorePlugin;

import java.util.ArrayList;
import java.util.List;

public class PluginRegistry {
    private final List<CorePlugin> plugins = new ArrayList<>();

    public void registerPlugin(CorePlugin plugin) {
        System.out.println("[PluginRegistry] Registering: " + plugin.name());
        plugins.add(plugin);
    }

    public List<CorePlugin> getPlugins() {
        return plugins;
    }

    public CorePlugin getPlugin(String name) {
        for (CorePlugin plugin : plugins) {
            if (plugin.name().equals(name)) {
                return plugin;
            }
        }
        return null;
    }

}
