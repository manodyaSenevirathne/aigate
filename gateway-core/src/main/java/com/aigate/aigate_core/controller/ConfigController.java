package com.aigate.aigate_core.controller;

import com.aigate.aigate_core.config.CentralConfigService;
import com.aigate.aigate_core.config.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final CentralConfigService configService;

    public ConfigController(CentralConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Config> getConfig(@PathVariable String name) {
        Config config = configService.getConfig(name);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(config);
    }

    @PostMapping("/{name}")
    public ResponseEntity<String> updateConfig(@PathVariable String name, @RequestBody Config config) {
        configService.updateConfig(name, config);
        return ResponseEntity.ok("Configuration updated");
    }
}
