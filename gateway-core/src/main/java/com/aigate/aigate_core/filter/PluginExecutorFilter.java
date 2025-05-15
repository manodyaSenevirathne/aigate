package com.aigate.aigate_core.filter;

import com.aigate.plugin.Plugin;
import com.aigate.plugin.PluginContext;
import com.aigate.plugin.PluginRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class PluginExecutorFilter implements WebFilter {

    @Autowired
    private PluginRegistry pluginRegistry;

    public PluginExecutorFilter() {
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        PluginContext context = new PluginContext();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        for (Plugin plugin : pluginRegistry.getPlugins()) {
            plugin.preRequest(request, context);
        }

        return chain.filter(exchange).doOnSuccess((v) -> {
            for (Plugin plugin : pluginRegistry.getPlugins()) {
                plugin.preResponse(request, response, context);
            }
        }).doOnError(error -> {
            for (Plugin plugin : pluginRegistry.getPlugins()) {
                plugin.onError(error, context);
            }
        });
    }
}