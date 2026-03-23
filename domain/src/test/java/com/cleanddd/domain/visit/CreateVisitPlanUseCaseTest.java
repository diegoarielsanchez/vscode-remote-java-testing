package com.cleanddd.domain.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.IHealthCareProfRepository;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.IMedicalSalesRepRepository;
import com.das.cleanddd.domain.shared.exceptions.BusinessValidationException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;
import com.das.cleanddd.domain.visit.IVisitPlanRepository;
import com.das.cleanddd.domain.visit.entities.VisitPlan;
import com.das.cleanddd.domain.visit.entities.VisitPlanFactory;
import com.das.cleanddd.domain.visit.usecases.dtos.CreateVisitPlanInputDTO;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitPlanMapper;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitPlanOutputDTO;
import com.das.cleanddd.domain.visit.usecases.services.CreateVisitPlanUseCase;

class CreateVisitPlanUseCaseTest {

    private IVisitPlanRepository visitPlanRepository;
    private IHealthCareProfRepository healthCareProfRepository;
    private IMedicalSalesRepRepository medicalSalesRepRepository;
    private VisitPlanFactory visitPlanFactory;
    private VisitPlanMapper mapper;
    private CreateVisitPlanUseCase useCase;

    @BeforeEach
    void setUp() {
        visitPlanRepository = mock(IVisitPlanRepository.class);
        healthCareProfRepository = mock(IHealthCareProfRepository.class);
        medicalSalesRepRepository = mock(IMedicalSalesRepRepository.class);
        visitPlanFactory = new VisitPlanFactory();
        mapper = mock(VisitPlanMapper.class);
        useCase = new CreateVisitPlanUseCase(visitPlanRepository, healthCareProfRepository, medicalSalesRepRepository, visitPlanFactory, mapper);
    }

    // ── Happy path ────────────────────────────────────────────────────────────

    @Test
    void shouldCreateVisitPlanWithTodayDateTime() throws DomainException {
        LocalDateTime today = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0).withNano(0);

        CreateVisitPlanInputDTO input = validInput(today);
        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(msr));

        VisitPlanOutputDTO expected = outputDTO(today, input);
        when(mapper.outputFromEntity(any(VisitPlan.class))).thenReturn(expected);

        VisitPlanOutputDTO result = useCase.execute(input);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(visitPlanRepository).save(any(VisitPlan.class));
    }

    @Test
    void shouldCreateVisitPlanWithFutureDateTime() throws DomainException {
        LocalDateTime future = LocalDateTime.now().plusDays(7);

        CreateVisitPlanInputDTO input = validInput(future);
        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(msr));

        VisitPlanOutputDTO expected = outputDTO(future, input);
        when(mapper.outputFromEntity(any(VisitPlan.class))).thenReturn(expected);

        VisitPlanOutputDTO result = useCase.execute(input);

        assertNotNull(result);
        verify(visitPlanRepository).save(any(VisitPlan.class));
    }

    // ── Date validation rule ──────────────────────────────────────────────────

    @Test
    void shouldThrowWhenVisitDateTimeIsYesterday() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        CreateVisitPlanInputDTO input = validInput(yesterday);
        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(msr));

        assertThrows(BusinessValidationException.class, () -> useCase.execute(input));
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenVisitDateTimeIsInThePast() {
        LocalDateTime pastDate = LocalDateTime.now().minusMonths(3);

        CreateVisitPlanInputDTO input = validInput(pastDate);
        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(msr));

        BusinessValidationException ex = assertThrows(BusinessValidationException.class,
                () -> useCase.execute(input));

        assertEquals("Visit date/time cannot be in the past.", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenVisitDateTimeIsNull() {
        CreateVisitPlanInputDTO input = new CreateVisitPlanInputDTO(
            null,
            UUID.randomUUID().toString(),
            "comments",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit date time cannot be null", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    // ── Input validation ──────────────────────────────────────────────────────

    @Test
    void shouldThrowWhenInputIsNull() {
        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(null));
        assertEquals("Input DTO cannot be null", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenHealthCareProfIdIsNull() {
        CreateVisitPlanInputDTO input = new CreateVisitPlanInputDTO(
            LocalDateTime.now().plusDays(1),
            null,
            "comments",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Health Care Professional id cannot be null or empty", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenVisitSiteIdIsNull() {
        CreateVisitPlanInputDTO input = new CreateVisitPlanInputDTO(
            LocalDateTime.now().plusDays(1),
            UUID.randomUUID().toString(),
            "comments",
            null,
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit site id cannot be null or empty", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenMedicalSalesRepIdIsNull() {
        CreateVisitPlanInputDTO input = new CreateVisitPlanInputDTO(
            LocalDateTime.now().plusDays(1),
            UUID.randomUUID().toString(),
            "comments",
            UUID.randomUUID().toString(),
            null
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Medical Sales Representative id cannot be null or empty", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    // ── Repository not found ──────────────────────────────────────────────────

    @Test
    void shouldThrowWhenHealthCareProfNotFound() {
        CreateVisitPlanInputDTO input = validInput(LocalDateTime.now().plusDays(1));

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Health Care Professional not found", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenMedicalSalesRepNotFound() {
        CreateVisitPlanInputDTO input = validInput(LocalDateTime.now().plusDays(1));
        HealthCareProf hcp = mock(HealthCareProf.class);

        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Medical Sales Representative not found", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private CreateVisitPlanInputDTO validInput(LocalDateTime dateTime) {
        return new CreateVisitPlanInputDTO(
            dateTime,
            UUID.randomUUID().toString(),
            "visit comments",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    private VisitPlanOutputDTO outputDTO(LocalDateTime dateTime, CreateVisitPlanInputDTO input) {
        return new VisitPlanOutputDTO(
            UUID.randomUUID().toString(),
            dateTime,
            input.healthCareProfId(),
            input.visitComments(),
            input.visitSiteId(),
            input.medicalSalesRepId()
        );
    }
}
