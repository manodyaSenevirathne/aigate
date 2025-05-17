package com.aigate.aigate_core.controller;

import com.aigate.aigate_core.dto.PluginInfo;
import com.aigate.aigate_core.interfaces.ExternalPlugin;
import com.aigate.aigate_core.interfaces.ExternalPluginMetadata;
import com.aigate.aigate_core.interfaces.Plugin;
import com.aigate.aigate_core.plugin.PluginRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plugins")
public class PluginController {

    private final PluginRegistry pluginRegistry;

    @Autowired
    public PluginController(PluginRegistry pluginRegistry) {
        this.pluginRegistry = pluginRegistry;
    }

    @PostMapping("/register")
    public String registerPlugin(@RequestBody ExternalPluginMetadata pluginMetadata, @RequestHeader(value = "X-Real-IP", required = false) String realIp) {
        // Use provided host or fallback to real IP
        if (pluginMetadata.getHost() == null || pluginMetadata.getHost().isEmpty()) {
            pluginMetadata.setHost(realIp != null ? realIp : "localhost");
        }
        ExternalPlugin plugin = new ExternalPlugin(pluginMetadata);
        pluginRegistry.registerPlugin(plugin);
        return "Plugin registered: " + plugin.name();
    }

    @GetMapping
    public List<PluginInfo> listPlugins() {
        return pluginRegistry.getPlugins().stream().map(plugin -> {
            String name = plugin.name();
            String type = plugin instanceof ExternalPlugin ? "external" : "core";
            String host = null;
            int port = -1;
            if (plugin instanceof ExternalPlugin external) {
                host = external.metadata.getHost();
                port = external.metadata.getPort();
            }
            return new PluginInfo(name, type, host, port);
        }).toList();

    }
}
