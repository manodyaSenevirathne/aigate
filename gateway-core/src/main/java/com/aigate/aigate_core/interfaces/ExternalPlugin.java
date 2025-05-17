package com.aigate.aigate_core.interfaces;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExternalPlugin implements Plugin{

    public ExternalPluginMetadata metadata;

    @Override
    public String name() {
        return metadata.getName();
    }
}
