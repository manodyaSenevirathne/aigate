package com.aigate.aigate_core.interfaces;

import com.aigate.aigate_core.models.ReqResContext;

public interface Plugin {
    String name();

    default void request(ReqResContext context){

    }

    default void response(ReqResContext context){

    }

    default void onError(Throwable error, ReqResContext context){

    }
}
