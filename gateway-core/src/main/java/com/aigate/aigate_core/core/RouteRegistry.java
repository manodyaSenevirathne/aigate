package com.aigate.aigate_core.core;

import com.aigate.aigate_core.models.RouteConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteRegistry {
    private final List<RouteConfig> routes = new ArrayList<>();

    public void registerRoute(RouteConfig config) {
        routes.add(config);
    }

    public Optional<RouteConfig> matchRoute(String path) {
        return routes.stream().filter(r -> path.matches(r.getPathPattern())).findFirst();
    }

    public RouteConfig[] getRoutes() {
        return routes.toArray(new RouteConfig[0]);
    }
}
