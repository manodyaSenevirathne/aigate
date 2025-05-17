package com.aigate.aigate_core.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalPluginMetadata {
    private String name;
    private String host;
    private int port;
    private Set<String> stages; // "request", "response", "both"
}
