package com.aigate.aigate_core.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class GatewayCoreFilter implements WebFilter {

    private final PluginExecutor executor;
    private final RateLimiter rateLimiter;

    public GatewayCoreFilter(PluginExecutor executor, RateLimiter rateLimiter) {
        this.executor = executor;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ReqResContext context = new ReqResContext();
        context.put("request", exchange.getRequest());
        context.put("response", exchange.getResponse());

        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        // rate limiting
        if (!rateLimiter.isAllowed(ip)){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS); // 429
            return response.setComplete();
        }

        executor.executePreRequest(context);
        return chain.filter(exchange).doOnSuccess((v) -> {
            executor.executePostRequest(context);
        }).doOnError(error -> {
            executor.executeOnError( error, context);
        });
    }
}