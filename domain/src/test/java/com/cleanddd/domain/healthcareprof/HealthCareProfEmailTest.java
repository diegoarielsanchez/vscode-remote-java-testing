package com.cleanddd.domain.healthcareprof;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;

class HealthCareProfEmailTest {

    @Test
    void shouldAllowNullEmail() {
        HealthCareProfEmail email = assertDoesNotThrow(() -> new HealthCareProfEmail(null));

        assertNull(email.value());
    }
}
