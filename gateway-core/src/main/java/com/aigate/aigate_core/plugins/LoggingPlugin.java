package com.aigate.aigate_core.plugins;

import com.aigate.plugin.Plugin;
import com.aigate.plugin.PluginContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class LoggingPlugin implements Plugin {
    @Override
    public String name() {
        return "LoggingPlugin";
    }

    @Override
    public void preRequest(ServerHttpRequest request, PluginContext context) {
        System.out.println("[LoggingPlugin] Incoming: " + request.getURI());
    }

    @Override
    public void preResponse(ServerHttpRequest request, ServerHttpResponse response, PluginContext context) {
        System.out.println("[LoggingPlugin] Outgoing response" + response.getStatusCode());
    }
}
