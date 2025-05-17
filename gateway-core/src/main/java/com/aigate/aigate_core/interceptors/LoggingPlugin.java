package com.aigate.aigate_core.interceptors;

import com.aigate.aigate_core.models.ReqResContext;
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
    public void request(ReqResContext context) {
        ServerHttpRequest request = (ServerHttpRequest) context.get("request");
        System.out.println("[LoggingPlugin] Incoming: " + request.getURI());
    }

    @Override
    public void response(ReqResContext context) {
        ServerHttpResponse response = (ServerHttpResponse) context.get("response");
        System.out.println("[LoggingPlugin] Outgoing response " + response.getStatusCode());
    }
}
