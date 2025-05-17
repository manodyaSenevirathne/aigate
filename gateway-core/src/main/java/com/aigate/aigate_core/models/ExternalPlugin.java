package com.aigate.aigate_core.models;

import com.aigate.aigate_core.interfaces.Plugin;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExternalPlugin implements Plugin {

    public ExternalPluginMetadata metadata;

    @Override
    public String name() {
        return metadata.getName();
    }
}
