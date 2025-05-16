package com.aigate.aigate_core.interfaces;

import com.aigate.aigate_core.core.ReqResContext;

public interface CorePlugin {
    String name();
    default void preRequest(ReqResContext context){

    }
    default void postRequest(ReqResContext context){

    }
    default void preResponse(ReqResContext context){

    }
    default void postResponse(ReqResContext context){

    }
    default void onError(Throwable error, ReqResContext context){

    }
}
