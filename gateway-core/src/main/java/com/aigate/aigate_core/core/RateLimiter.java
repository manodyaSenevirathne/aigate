package com.aigate.aigate_core.core;

import com.aigate.aigate_core.config.CentralConfigService;
import com.aigate.aigate_core.config.Config;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class RateLimiter {

    @Autowired
    private CentralConfigService configService;

    private long windowSizeInMillis;
    private int maxRequestsPerWindow;

    private final Map<String, Deque<Long>> requestTimes = new HashMap<>();


    @PostConstruct
    public void init() {
        loadConfig();
    }

    private void loadConfig() throws RuntimeException{
        Config config = configService.getConfig("rate-limiter");
        if (config == null) {
            throw new RuntimeException("❌ Missing rateLimiter config");
        }
        try {
            windowSizeInMillis = Long.parseLong(config.get("window-size-in-millis"));
            maxRequestsPerWindow = Integer.parseInt(config.get("max-requests"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("❌ Invalid rateLimiter settings", e);
        }

        System.out.println("✅ Loaded rateLimiter config: " + windowSizeInMillis + "ms / " + maxRequestsPerWindow + " reqs");
    }

    public synchronized boolean isAllowed(String clientIp) {
        long now = System.currentTimeMillis();
        Deque<Long> timestamps = requestTimes.computeIfAbsent(clientIp, k -> new LinkedList<>());

        while (!timestamps.isEmpty() && (now - timestamps.peekFirst()) > windowSizeInMillis) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < maxRequestsPerWindow) {
            timestamps.addLast(now);
            return true;
        } else {
            return false;
        }
    }

    // Optional: Add a method to refresh config at runtime
    public void refreshConfig() {
        //
    }
}
