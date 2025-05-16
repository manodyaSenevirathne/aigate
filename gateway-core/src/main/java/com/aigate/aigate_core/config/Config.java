package com.aigate.aigate_core.config;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private Map<String, String> settings = new HashMap<>();

    public Config(Map<String, String> settings) {
        this.settings = settings;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    public String get(String key) {
        return settings.get(key);
    }

    public void put(String key, String value) {
        settings.put(key, value);
    }
}

