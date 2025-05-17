package com.aigate.aigate_core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PluginInfo {
    String name;
    String type;
    String host;
    int port;
}
