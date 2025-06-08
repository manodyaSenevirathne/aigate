package com.aigate.aigate_core.models;

import com.aigate.aigate_core.enums.InboundProtocol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteConfig {
    private String id;
    private String pathPattern;
    private InboundProtocol protocol;
    private String host;
    private int port;

}
