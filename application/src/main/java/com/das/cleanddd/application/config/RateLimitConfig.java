package com.das.cleanddd.application.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public Bucket createNewBucket() {
        // Allow 100 requests per minute with burst capacity of 10
        return Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(100, Duration.ofMinutes(1))))
                .build();
    }
}