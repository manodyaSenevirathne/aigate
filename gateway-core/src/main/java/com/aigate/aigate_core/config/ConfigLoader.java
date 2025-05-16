package com.aigate.aigate_core.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "aigate-config")
public class ConfigLoader {
    private Map<String, String> config = new HashMap<>();

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    @Autowired
    private CentralConfigService centralConfigService;

    @PostConstruct
    public void init() {
        if (config == null || config.isEmpty()) {
            System.out.println("⚠️ No configuration loaded from aigate-config");
            return;
        }

        Map<String, Map<String, String>> grouped = new HashMap<>();

        for (Map.Entry<String, String> entry : config.entrySet()) {
            String[] parts = entry.getKey().split("\\.", 2);
            if (parts.length == 2) {
                grouped.computeIfAbsent(parts[0], k -> new HashMap<>()).put(parts[1], entry.getValue());
            }
        }

        for (Map.Entry<String, Map<String, String>> entry : grouped.entrySet()) {
            centralConfigService.updateConfig(entry.getKey(), new Config(entry.getValue()));
        }

        System.out.println("✅ Central configuration initialized with groups: " + grouped.keySet());
    }
}
