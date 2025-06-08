package com.aigate.aigate_core.controller;

import com.aigate.aigate_core.core.RouterHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class routeContoller {


    final RouterHandler routerHandler = new RouterHandler();

//    @GetMapping("/route/**")
//    public Mono<Void> route(ServerHttpRequest request, ServerHttpResponse response) {
//        return routerHandler.route(request, response);
//    }

    @GetMapping("/routes")
    public Mono<String> getRoutes() {
        return Mono.just(routerHandler.getRoutes());
    }

}
