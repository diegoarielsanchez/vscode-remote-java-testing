package com.das.infrasqlserver.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.shared.UuidGenerator;

@Service
public final class JavaUuidGenerator implements UuidGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
