package com.deliverytech.delivery.controller;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private static final String SERVICE_NAME = "Delivery API";

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now().toString(),
            "service", SERVICE_NAME,
            "javaVersion", System.getProperty("java.version")
        );
    }

    @GetMapping("/info")
    public AppInfo info() {
        String description = """
            Delivery Tech API
            Base inicial com Spring Boot e JDK 21
            """.trim();
        return new AppInfo(
            description,
            "1.0.0",
            "DeliveryTech Team",
            "JDK 21",
            "Spring Boot 3.2.x"
        );
    }

    public record AppInfo(
        String application,
        String version,
        String developer,
        String javaVersion,
        String framework
    ) {
    }
}
