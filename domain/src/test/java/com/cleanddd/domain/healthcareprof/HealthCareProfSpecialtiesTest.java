package com.cleanddd.domain.healthcareprof;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
    void shouldAllowNullEmailWhenEmailObjectIsNullInConstructor() {
        HealthCareProf healthCareProf = new HealthCareProf(
                HealthCareProfId.random(),
                new HealthCareProfName("Diego"),
                new HealthCareProfName("Sanchez"),
                null,
                new HealthCareProfActive(false),
                List.of(s("Cardiology"))
        );

        assertNotNull(healthCareProf.getEmail());
        assertNull(healthCareProf.getEmail().value());
    }

    @Test
    void shouldThrowWhenCreatingWithMoreThanSevenSpecialties() {
        List<Specialty> specialties = createSpecialties(8);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> createHealthCareProf(specialties));

        assertEquals(HealthCareProf.ERROR_MESSAGE_MAX_SPECIALTIES, ex.getMessage());
    }

    @Test
    void shouldDefensivelyCopySpecialtiesInConstructor() {
        List<Specialty> inputSpecialties = new ArrayList<>();
        inputSpecialties.add(s("Cardiology"));

        HealthCareProf healthCareProf = createHealthCareProf(inputSpecialties);
        inputSpecialties.add(s("Dermatology"));

        assertEquals(1, healthCareProf.getSpecialties().size());
        assertEquals("Cardiology", healthCareProf.getSpecialties().get(0).name());
    }

    @Test
    void shouldAddSpecialtyWhenNotPresent() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology")));

        HealthCareProf updated = healthCareProf.addSpecialty(s("Pediatrics"));

        assertNotSame(healthCareProf, updated);
        assertEquals(2, updated.getSpecialties().size());
        assertTrue(updated.getSpecialties().contains(s("Cardiology")));
        assertTrue(updated.getSpecialties().contains(s("Pediatrics")));
    }

    @Test
    void shouldThrowWhenAddingEighthSpecialty() {
        HealthCareProf healthCareProf = createHealthCareProf(createSpecialties(7));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> healthCareProf.addSpecialty(s("Specialty H")));

        assertEquals(HealthCareProf.ERROR_MESSAGE_MAX_SPECIALTIES, ex.getMessage());
    }

    @Test
    void shouldReturnSameInstanceWhenAddingDuplicateSpecialty() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology")));

        HealthCareProf updated = healthCareProf.addSpecialty(s("Cardiology"));

        assertSame(healthCareProf, updated);
    }

    @Test
    void shouldThrowWhenAddingNullSpecialty() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.addSpecialty(null));
    }

    @Test
    void shouldRemoveExistingSpecialty() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology"), s("Pediatrics")));

        HealthCareProf updated = healthCareProf.removeSpecialty(s("Pediatrics"));

        assertNotSame(healthCareProf, updated);
        assertEquals(1, updated.getSpecialties().size());
        assertEquals("Cardiology", updated.getSpecialties().get(0).name());
    }

    @Test
    void shouldReturnSameInstanceWhenRemovingNonExistingSpecialty() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology"), s("Pediatrics")));

        HealthCareProf updated = healthCareProf.removeSpecialty(s("Neurology"));

        assertSame(healthCareProf, updated);
    }

    @Test
    void shouldThrowWhenRemovingLastSpecialty() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.removeSpecialty(s("Cardiology")));
    }

    @Test
    void shouldReturnSameInstanceWhenChangingToSameSpecialties() throws Exception {
        List<Specialty> specialties = List.of(s("Cardiology"), s("Pediatrics"));
        HealthCareProf healthCareProf = createHealthCareProf(specialties);

        HealthCareProf updated = healthCareProf.changeSpecialties(List.of(s("Cardiology"), s("Pediatrics")));

        assertSame(healthCareProf, updated);
    }

    @Test
    void shouldThrowWhenChangingSpecialtiesToNull() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.changeSpecialties(null));
    }

    @Test
    void shouldThrowWhenChangingSpecialtiesToEmpty() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology")));

        assertThrows(RequiredFieldException.class, () -> healthCareProf.changeSpecialties(List.of()));
    }

    @Test
    void shouldReturnNewInstanceWhenChangingSpecialtiesToDifferentValues() throws Exception {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology"), s("Pediatrics")));

        HealthCareProf updated = healthCareProf.changeSpecialties(List.of(s("Neurology"), s("Dermatology")));

        assertNotSame(healthCareProf, updated);
        assertEquals(2, updated.getSpecialties().size());
        assertTrue(updated.getSpecialties().contains(s("Neurology")));
        assertTrue(updated.getSpecialties().contains(s("Dermatology")));
    }

    @Test
    void shouldThrowWhenChangingSpecialtiesToMoreThanSeven() {
        HealthCareProf healthCareProf = createHealthCareProf(List.of(s("Cardiology")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> healthCareProf.changeSpecialties(createSpecialties(8)));

        assertEquals(HealthCareProf.ERROR_MESSAGE_MAX_SPECIALTIES, ex.getMessage());
    }

    private List<Specialty> createSpecialties(int size) {
        List<Specialty> specialties = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            specialties.add(s("Specialty " + toAlphabeticSuffix(i)));
        }
        return specialties;
    }

    private Specialty s(String name) {
        return new Specialty(name, name);
    }

    private String toAlphabeticSuffix(int index) {
        int zeroBased = index - 1;
        StringBuilder suffix = new StringBuilder();

        do {
            suffix.insert(0, (char) ('A' + (zeroBased % 26)));
            zeroBased = (zeroBased / 26) - 1;
        } while (zeroBased >= 0);

        return suffix.toString();
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
