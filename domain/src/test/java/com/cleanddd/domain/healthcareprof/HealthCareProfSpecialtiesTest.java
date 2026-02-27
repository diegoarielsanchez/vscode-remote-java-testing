package com.cleanddd.domain.healthcareprof;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfActive;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.healthcareprof.entities.Specialty;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;

class HealthCareProfSpecialtiesTest {

    @Test
    void shouldDefensivelyCopySpecialtiesInConstructor() {
        List<Specialty> inputSpecialties = new ArrayList<>();
        inputSpecialties.add(new Specialty("Cardiology"));

        HealthCareProf healthCareProf = createHealthCareProf(inputSpecialties);
        inputSpecialties.add(new Specialty("Dermatology"));

        assertEquals(1, healthCareProf.getSpecialties().size());
        assertEquals("Cardiology", healthCareProf.getSpecialties().get(0).value());
    }

    @Test
    void shouldAddSpecialtyWhenNotPresent() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology")));

        HealthCareProf updated = healthCareProf.addSpecialty(new Specialty("Pediatrics"));

        assertNotSame(healthCareProf, updated);
        assertEquals(2, updated.getSpecialties().size());
        assertTrue(updated.getSpecialties().contains(new Specialty("Cardiology")));
        assertTrue(updated.getSpecialties().contains(new Specialty("Pediatrics")));
    }

    @Test
    void shouldReturnSameInstanceWhenAddingDuplicateSpecialty() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology")));

        HealthCareProf updated = healthCareProf.addSpecialty(new Specialty("Cardiology"));

        assertSame(healthCareProf, updated);
    }

    @Test
    void shouldThrowWhenAddingNullSpecialty() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.addSpecialty(null));
    }

    @Test
    void shouldRemoveExistingSpecialty() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology"), new Specialty("Pediatrics")));

        HealthCareProf updated = healthCareProf.removeSpecialty(new Specialty("Pediatrics"));

        assertNotSame(healthCareProf, updated);
        assertEquals(1, updated.getSpecialties().size());
        assertEquals("Cardiology", updated.getSpecialties().get(0).value());
    }

    @Test
    void shouldReturnSameInstanceWhenRemovingNonExistingSpecialty() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology"), new Specialty("Pediatrics")));

        HealthCareProf updated = healthCareProf.removeSpecialty(new Specialty("Neurology"));

        assertSame(healthCareProf, updated);
    }

    @Test
    void shouldThrowWhenRemovingLastSpecialty() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.removeSpecialty(new Specialty("Cardiology")));
    }

    @Test
    void shouldReturnSameInstanceWhenChangingToSameSpecialties() throws Exception {
        List<Specialty> specialties = List.of(new Specialty("Cardiology"), new Specialty("Pediatrics"));
        HealthCareProf healthCareProf = createHealthCareProf(specialties);

        HealthCareProf updated = healthCareProf.changeSpecialties(List.of(new Specialty("Cardiology"), new Specialty("Pediatrics")));

        assertSame(healthCareProf, updated);
    }

    @Test
    void shouldThrowWhenChangingSpecialtiesToNull() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.changeSpecialties(null));
    }

    @Test
    void shouldThrowWhenChangingSpecialtiesToEmpty() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.changeSpecialties(List.of()));
    }

    @Test
    void shouldReturnNewInstanceWhenChangingSpecialtiesToDifferentValues() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(new Specialty("Cardiology"), new Specialty("Pediatrics")));

        HealthCareProf updated = healthCareProf.changeSpecialties(List.of(new Specialty("Neurology"), new Specialty("Dermatology")));

        assertNotSame(healthCareProf, updated);
        assertEquals(2, updated.getSpecialties().size());
        assertTrue(updated.getSpecialties().contains(new Specialty("Neurology")));
        assertTrue(updated.getSpecialties().contains(new Specialty("Dermatology")));
    }

    private HealthCareProf createHealthCareProf(List<Specialty> specialties) {
        return new HealthCareProf(
                HealthCareProfId.random(),
                new HealthCareProfName("Diego"),
                new HealthCareProfName("Sanchez"),
                new HealthCareProfEmail("doctor@example.com"),
                new HealthCareProfActive(false),
                specialties
        );
    }
}
