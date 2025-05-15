package com.aigate.aigate_core.plugins;

import com.aigate.aigate_core.plugin.Plugin;
import com.aigate.aigate_core.plugin.PluginContext;
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
    public void preRequest(PluginContext context) {
        ServerHttpRequest request = (ServerHttpRequest) context.get("request");
        System.out.println("[LoggingPlugin] Incoming: " + request.getURI());
    }

    @Override
    public void preResponse(PluginContext context) {
        ServerHttpResponse response = (ServerHttpResponse) context.get("response");
        System.out.println("[LoggingPlugin] Outgoing response " + response.getStatusCode());
    }
}
