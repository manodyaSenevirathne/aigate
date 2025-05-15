package com.aigate.plugin;

import java.util.ArrayList;
import java.util.List;

public class PluginRegistry {
    private final List<Plugin> plugins = new ArrayList<>();

    public void registerPlugin(Plugin plugin) {
        System.out.println("[PluginRegistry] Registering: " + plugin.name());
        plugins.add(plugin);
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public Plugin getPlugin(String name) {
        for (Plugin plugin : plugins) {
            if (plugin.name().equals(name)) {
                return plugin;
            }
        }
        return null;
    }

}
