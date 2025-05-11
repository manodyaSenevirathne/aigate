package com.aigate.aigate_core.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from AI-Gate++ Core 🚀";
    }
}
