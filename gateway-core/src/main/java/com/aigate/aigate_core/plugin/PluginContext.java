package com.aigate.aigate_core.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PluginContext {
    private final Map<String, Object> attributes = new HashMap<>();

    public void put(String key, Object value) {
        attributes.put(key, value);
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
