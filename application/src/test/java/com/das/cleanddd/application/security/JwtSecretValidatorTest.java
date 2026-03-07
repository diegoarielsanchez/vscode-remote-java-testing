package com.das.cleanddd.application.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtSecretValidatorTest {

    @Test
    void shouldRejectBlankSecret() {
        JwtSecretValidator validator = new JwtSecretValidator();
        ReflectionTestUtils.setField(validator, "jwtSecret", "");

        assertThrows(IllegalStateException.class, validator::validate);
    }

    @Test
    void shouldRejectShortSecret() {
        JwtSecretValidator validator = new JwtSecretValidator();
        ReflectionTestUtils.setField(validator, "jwtSecret", "short-secret");

        assertThrows(IllegalStateException.class, validator::validate);
    }

    @Test
    void shouldRejectPlaceholderSecret() {
        JwtSecretValidator validator = new JwtSecretValidator();
        ReflectionTestUtils.setField(validator, "jwtSecret", "your-secret-key-change-this-in-production");

        assertThrows(IllegalStateException.class, validator::validate);
    }

    @Test
    void shouldAcceptStrongSecret() {
        JwtSecretValidator validator = new JwtSecretValidator();
        ReflectionTestUtils.setField(validator, "jwtSecret", "strong-secure-jwt-secret-with-32-plus-chars");

        assertDoesNotThrow(validator::validate);
    }
}
