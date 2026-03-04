package com.cleanddd.domain.medicalsalesrep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;

class MedicalSalesRepEmailTest {

    @Test
    void shouldThrowWhenEmailIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new MedicalSalesRepEmail(null));

        assertEquals(MedicalSalesRepEmail.ERROR_MESSAGE_NULL, ex.getMessage());
    }
}
