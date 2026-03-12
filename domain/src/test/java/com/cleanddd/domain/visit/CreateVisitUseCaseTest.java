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
import com.das.cleanddd.domain.shared.exceptions.BusinessValidationException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;
import com.das.cleanddd.domain.visit.IVisitRepository;
import com.das.cleanddd.domain.visit.entities.Visit;
import com.das.cleanddd.domain.visit.usecases.dtos.CreateVisitInputDTO;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitMapper;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitOutputDTO;
import com.das.cleanddd.domain.visit.usecases.services.CreateVisitUseCase;

class CreateVisitUseCaseTest {

    private IVisitRepository visitRepository;
    private HealthCareProfRepository healthCareProfRepository;
    private MedicalSalesRepRepository medicalSalesRepRepository;
    private VisitMapper mapper;
    private CreateVisitUseCase useCase;

    @BeforeEach
    void setUp() {
        visitRepository = mock(IVisitRepository.class);
        healthCareProfRepository = mock(HealthCareProfRepository.class);
        medicalSalesRepRepository = mock(MedicalSalesRepRepository.class);
        mapper = mock(VisitMapper.class);
        useCase = new CreateVisitUseCase(visitRepository, healthCareProfRepository, medicalSalesRepRepository, mapper);
    }

    @Test
    void shouldCreateVisit() throws DomainException {
        String healthCareProfId = UUID.randomUUID().toString();
        String medicalSalesRepId = UUID.randomUUID().toString();
        String visitSiteId = UUID.randomUUID().toString();

        CreateVisitInputDTO input = new CreateVisitInputDTO(
            LocalDate.now(),
            healthCareProfId,
            "visit comments",
            visitSiteId,
            medicalSalesRepId
        );

        HealthCareProf healthCareProf = mock(HealthCareProf.class);
        MedicalSalesRep medicalSalesRep = mock(MedicalSalesRep.class);
        when(healthCareProf.isActive()).thenReturn(true);
        when(medicalSalesRep.isActive()).thenReturn(true);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(healthCareProf));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(medicalSalesRep));

        VisitOutputDTO expected = new VisitOutputDTO(
            UUID.randomUUID().toString(),
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
    void shouldThrowWhenInputIsNull() {
        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(null));
        assertEquals("Input DTO cannot be null", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenHealthCareProfNotFound() {
        CreateVisitInputDTO input = new CreateVisitInputDTO(
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Health Care Professional not found", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenMedicalSalesRepNotFound() {
        CreateVisitInputDTO input = new CreateVisitInputDTO(
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        HealthCareProf healthCareProf = mock(HealthCareProf.class);
        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(healthCareProf));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Medical Sales Representative not found", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenVisitDateIsInTheFuture() {
        CreateVisitInputDTO input = new CreateVisitInputDTO(
            LocalDate.now().plusDays(1),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        HealthCareProf healthCareProf = mock(HealthCareProf.class);
        MedicalSalesRep medicalSalesRep = mock(MedicalSalesRep.class);
        when(healthCareProf.isActive()).thenReturn(true);
        when(medicalSalesRep.isActive()).thenReturn(true);
        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(healthCareProf));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(medicalSalesRep));

        BusinessValidationException ex = assertThrows(BusinessValidationException.class, () -> useCase.execute(input));
        assertEquals("Visit date cannot be later than today.", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenMedicalSalesRepIsInactive() {
        CreateVisitInputDTO input = new CreateVisitInputDTO(
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        HealthCareProf healthCareProf = mock(HealthCareProf.class);
        MedicalSalesRep medicalSalesRep = mock(MedicalSalesRep.class);
        when(healthCareProf.isActive()).thenReturn(true);
        when(medicalSalesRep.isActive()).thenReturn(false);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(healthCareProf));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(medicalSalesRep));

        BusinessValidationException ex = assertThrows(BusinessValidationException.class, () -> useCase.execute(input));
        assertEquals("Medical Sales Representative must be active.", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }

    @Test
    void shouldThrowWhenHealthCareProfIsInactive() {
        CreateVisitInputDTO input = new CreateVisitInputDTO(
            LocalDate.now(),
            UUID.randomUUID().toString(),
            "notes",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        HealthCareProf healthCareProf = mock(HealthCareProf.class);
        MedicalSalesRep medicalSalesRep = mock(MedicalSalesRep.class);
        when(healthCareProf.isActive()).thenReturn(false);
        when(medicalSalesRep.isActive()).thenReturn(true);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(healthCareProf));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(medicalSalesRep));

        BusinessValidationException ex = assertThrows(BusinessValidationException.class, () -> useCase.execute(input));
        assertEquals("Health Care Professional must be active.", ex.getMessage());
        verify(visitRepository, never()).save(any(Visit.class));
    }
}
