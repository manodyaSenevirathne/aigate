package com.aigate.aigate_core.config;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CentralConfigService {

    private final ConcurrentMap<String, Config> configMap = new ConcurrentHashMap<>();

    public CentralConfigService() {

    }

    public Config getConfig(String name) {
        return configMap.get(name);
    }

    public void updateConfig(String name, Config config) {
        configMap.put(name, config);
    }
}
