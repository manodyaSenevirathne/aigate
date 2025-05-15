package com.aigate.aigate_core;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class RateLimiter {

    private final Map<String, Deque<Long>> requestHistory = new HashMap<>();
    private final int MAX_REQUESTS = 5;
    private final int WINDOW_SECONDS = 10;

    public synchronized boolean isAllowed(String ip) {
        long now = Instant.now().getEpochSecond();
        Deque<Long> timestamps = requestHistory.computeIfAbsent(ip, k -> new LinkedList<>());

        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > WINDOW_SECONDS) {
            timestamps.pollFirst(); // Remove old timestamps
        }

        if (timestamps.size() >= MAX_REQUESTS) {
            return false; // Block
        }

        timestamps.addLast(now); // Allow
        return true;
    }
}