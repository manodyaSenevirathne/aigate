package com.aigate.aigate_core.plugin;

import com.aigate.aigate_core.interfaces.CorePlugin;
import com.aigate.aigate_core.interfaces.Plugin;
import com.aigate.aigate_core.models.ExternalPlugin;

import java.util.ArrayList;
import java.util.List;

public class PluginRegistry {
    private final List<CorePlugin> corePlugins = new ArrayList<>();
    private final List<ExternalPlugin> externalPlugins = new ArrayList<>();


    public void registerPlugin(CorePlugin plugin) {
        System.out.println("[PluginRegistry] Registering Core Plugin: " + plugin.name());
        corePlugins.add(plugin);
    }

    public void registerPlugin(ExternalPlugin plugin) {
        System.out.println("[PluginRegistry] Registering External Plugin: " + plugin.name());
        externalPlugins.add(plugin);
    }

    public List<Plugin> getPlugins() {
        List<Plugin> all = new ArrayList<>();
        all.addAll(corePlugins);
        all.addAll(externalPlugins);
        return all;
    }

    public Plugin getPlugin(String name) {
        for (Plugin plugin : getPlugins()) {
            if (plugin.name().equals(name)) {
                return plugin;
            }
        }
        return null;
    }


}
