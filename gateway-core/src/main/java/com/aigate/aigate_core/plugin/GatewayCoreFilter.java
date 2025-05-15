package com.aigate.aigate_core.plugin;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class GatewayCoreFilter implements WebFilter {

    private final PluginExecutor executor;

    public GatewayCoreFilter(PluginExecutor executor) {
        this.executor = executor;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        PluginContext context = new PluginContext();
        context.put("request", exchange.getRequest());
        context.put("response", exchange.getResponse());


        executor.executePreRequest(context);
        return chain.filter(exchange).doOnSuccess((v) -> {
            executor.executePostRequest(context);
        }).doOnError(error -> {
            executor.executeOnError( error, context);
        });
    }
}