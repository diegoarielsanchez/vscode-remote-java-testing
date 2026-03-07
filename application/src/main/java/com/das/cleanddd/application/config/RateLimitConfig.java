package com.das.cleanddd.application.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public Bandwidth rateLimitBandwidth() {
        // Allow 100 requests per minute with burst capacity of 10
        return Bandwidth.classic(10, Refill.intervally(100, Duration.ofMinutes(1)));
    }
}