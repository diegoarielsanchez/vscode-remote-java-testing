package com.cleanddd.domain.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfRepository;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.shared.exceptions.DomainException;
import com.das.cleanddd.domain.visit.IVisitRepository;
import com.das.cleanddd.domain.visit.entities.Visit;
import com.das.cleanddd.domain.visit.entities.VisitId;
import com.das.cleanddd.domain.visit.usecases.dtos.UpdateVisitInputDTO;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitMapper;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitOutputDTO;
import com.das.cleanddd.domain.visit.usecases.services.UpdateVisitUseCase;

class UpdateVisitUseCaseTest {

    private IVisitRepository visitRepository;
    private HealthCareProfRepository healthCareProfRepository;
    private MedicalSalesRepRepository medicalSalesRepRepository;
    private VisitMapper mapper;
    private UpdateVisitUseCase useCase;

    @BeforeEach
    void setUp() {
        visitRepository = mock(IVisitRepository.class);
        healthCareProfRepository = mock(HealthCareProfRepository.class);
        medicalSalesRepRepository = mock(MedicalSalesRepRepository.class);
        mapper = mock(VisitMapper.class);
        useCase = new UpdateVisitUseCase(visitRepository, healthCareProfRepository, medicalSalesRepRepository, mapper);
    }

    @Test
    void shouldThrowWhenInputIsNull() {
        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(null));
        assertEquals("Input DTO cannot be null", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenIdIsNull() {
        UpdateVisitInputDTO input = new UpdateVisitInputDTO(
            null,
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit id cannot be null or empty", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenVisitDateIsNull() {
        UpdateVisitInputDTO input = new UpdateVisitInputDTO(
            UUID.randomUUID().toString(),
            null,
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit date cannot be null", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenHealthCareProfIdIsNull() {
        UpdateVisitInputDTO input = new UpdateVisitInputDTO(
            UUID.randomUUID().toString(),
            LocalDate.now(),
            null,
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Health Care Professional id cannot be null or empty", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenVisitSiteIdIsNull() {
        UpdateVisitInputDTO input = new UpdateVisitInputDTO(
            UUID.randomUUID().toString(),
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            null,
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit site id cannot be null or empty", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenMedicalSalesRepIdIsNull() {
        UpdateVisitInputDTO input = new UpdateVisitInputDTO(
            UUID.randomUUID().toString(),
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            null
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Medical Sales Representative id cannot be null or empty", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldUpdateVisit() throws DomainException {
        String id = UUID.randomUUID().toString();
        String healthCareProfId = UUID.randomUUID().toString();
        String medicalSalesRepId = UUID.randomUUID().toString();
        String visitSiteId = UUID.randomUUID().toString();

        UpdateVisitInputDTO input = new UpdateVisitInputDTO(
            id,
            LocalDate.now(),
            healthCareProfId,
            "updated comments",
            visitSiteId,
            medicalSalesRepId
        );

        Visit existingVisit = mock(Visit.class);
        HealthCareProf healthCareProf = mock(HealthCareProf.class);
        MedicalSalesRep medicalSalesRep = mock(MedicalSalesRep.class);

        when(visitRepository.search(any(VisitId.class))).thenReturn(Optional.of(existingVisit));
        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(healthCareProf));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(medicalSalesRep));

        VisitOutputDTO expected = new VisitOutputDTO(
            id,
            input.visitDate(),
            input.healthCareProfId(),
            input.visitComments(),
            input.visitSiteId(),
            input.medicalSalesRepId()
        );
        when(mapper.outputFromEntity(any(Visit.class))).thenReturn(expected);

        VisitOutputDTO result = useCase.execute(input);

        assertEquals(expected, result);
        verify(visitRepository).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenVisitNotFound() {
        UpdateVisitInputDTO input = new UpdateVisitInputDTO(
            UUID.randomUUID().toString(),
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        when(visitRepository.search(any(VisitId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit not found.", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }
}
