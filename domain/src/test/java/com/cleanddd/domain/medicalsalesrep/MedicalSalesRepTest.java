package com.cleanddd.domain.medicalsalesrep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepActive;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;

class MedicalSalesRepTest {

    @Test
    void shouldThrowWhenEmailObjectIsNullInConstructor() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new MedicalSalesRep(
                        MedicalSalesRepId.random(),
                        new MedicalSalesRepName("Diego"),
                        new MedicalSalesRepName("Sanchez"),
                        null,
                        new MedicalSalesRepActive(false)
                ));

        assertEquals(MedicalSalesRepEmail.ERROR_MESSAGE_NULL, ex.getMessage());
    }
}
