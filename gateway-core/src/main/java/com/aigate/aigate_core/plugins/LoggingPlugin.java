package com.aigate.aigate_core.plugins;

import com.aigate.aigate_core.core.ReqResContext;
import com.aigate.aigate_core.interfaces.CorePlugin;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class LoggingPlugin implements CorePlugin {
    @Override
    public String name() {
        return "LoggingPlugin";
    }

    @Override
    public void preRequest(ReqResContext context) {
        ServerHttpRequest request = (ServerHttpRequest) context.get("request");
        System.out.println("[LoggingPlugin] Incoming: " + request.getURI());
    }

    @Override
    public void preResponse(ReqResContext context) {
        ServerHttpResponse response = (ServerHttpResponse) context.get("response");
        System.out.println("[LoggingPlugin] Outgoing response " + response.getStatusCode());
    }
}
