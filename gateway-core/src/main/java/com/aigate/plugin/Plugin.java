package com.aigate.plugin;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

public interface Plugin {
    String name();
    default void preRequest(ServerHttpRequest request, PluginContext context){

    }
    default void postRequest(ServerHttpRequest request, PluginContext context){

    }
    default void preResponse(ServerHttpRequest request, ServerHttpResponse response, PluginContext context){

    }
    default void postResponse(ServerHttpRequest request, ServerHttpResponse response, PluginContext context){

    }
    default void onError(Throwable error, PluginContext context){

    }
}
