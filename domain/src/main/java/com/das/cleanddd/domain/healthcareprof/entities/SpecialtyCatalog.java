package com.das.cleanddd.domain.healthcareprof.entities;

import java.util.List;
import java.util.Map;

import com.das.cleanddd.domain.shared.exceptions.DomainException;

public final class SpecialtyCatalog {

    private static final Map<String, String> PREDEFINED_SPECIALTIES = Map.of(
        "CARD", "Cardiology",
        "DERM", "Dermatology",
        "NEUR", "Neurology",
        "PED", "Pediatrics",
        "ORTH", "Orthopedics",
        "ONCO", "Oncology",
        "PSYC", "Psychiatry"
    );

    private SpecialtyCatalog() {
    }

    public static Specialty fromCode(String code) throws DomainException {
        String normalizedCode = normalizeCode(code);
        String name = PREDEFINED_SPECIALTIES.get(normalizedCode);

        if (name == null) {
            throw new DomainException("Invalid specialty code: " + code);
        }

        return new Specialty(normalizedCode, name);
    }

    public static List<Specialty> all() {
        return PREDEFINED_SPECIALTIES.entrySet().stream()
            .map(entry -> new Specialty(entry.getKey(), entry.getValue()))
            .toList();
    }

    private static String normalizeCode(String code) {
        return code == null ? "" : code.trim().toUpperCase();
    }
}
