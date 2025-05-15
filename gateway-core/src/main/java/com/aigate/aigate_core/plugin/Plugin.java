package com.aigate.aigate_core.plugin;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

public interface Plugin {
    String name();
    default void preRequest(PluginContext context){

    }
    default void postRequest(PluginContext context){

    }
    default void preResponse(PluginContext context){

    }
    default void postResponse(PluginContext context){

    }
    default void onError(Throwable error, PluginContext context){

    }
}
