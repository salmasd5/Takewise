package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok(Map.of("message", "If you see this, you are authenticated!"));
    }
} 