package com.aigate.aigate_core.core;

import com.aigate.aigate_core.models.ReqResContext;
import com.aigate.aigate_core.models.RouteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class RouterHandler {

    @Autowired
    RouteRegistry routeRegistry;

    public Mono<Void> route(ServerHttpRequest request, ServerHttpResponse response) {
//        ServerHttpRequest request = (ServerHttpRequest) context.get("request");
//        ServerHttpResponse response = (ServerHttpResponse) context.get("response");
        String path = request.getPath().toString();
        Optional<RouteConfig> match = routeRegistry.matchRoute(path);
        if (match.isEmpty()) {
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return response.setComplete();
        }

        RouteConfig route = match.get();
        return switch (route.getProtocol()) {
            case HTTP -> forwardHttp(request, response, route);
            case TCP -> forwardTcp(request, response, route);
            default -> {
                response.setStatusCode(HttpStatus.NOT_IMPLEMENTED);
                yield response.setComplete();
            }
        };
    }

    private Mono<Void> forwardHttp(ServerHttpRequest request, ServerHttpResponse response, RouteConfig route) {
        // forward via WebClient or HttpClient
        return WebClient.create("http://" + route.getHost() + ":" + route.getPort())
                .method(request.getMethod())
                .uri(request.getURI().getPath())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(body -> {
                    response.setStatusCode(HttpStatus.OK);
                    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer));
                });
    }

    private Mono<Void> forwardTcp(ServerHttpRequest request, ServerHttpResponse response, RouteConfig route) {
        // encode request, send via TCP (can reuse plugin-style TCP frame)
        // TODO: implement later with Netty client or Socket
        return Mono.fromRunnable(() -> {
            System.out.println("Routing to TCP: " + route.getHost() + ":" + route.getPort());
        }).then();
    }

    public String getRoutes() {
        StringBuilder sb = new StringBuilder();
        for (RouteConfig route : routeRegistry.getRoutes()) {
            sb.append(route.toString()).append("\n");
        }
        return sb.toString();
    }
}
