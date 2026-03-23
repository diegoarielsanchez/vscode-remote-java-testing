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
import com.das.cleanddd.domain.visit.entities.VisitId;
import com.das.cleanddd.domain.visit.entities.VisitPlan;
import com.das.cleanddd.domain.visit.usecases.dtos.UpdateVisitPlanInputDTO;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitPlanMapper;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitPlanOutputDTO;
import com.das.cleanddd.domain.visit.usecases.services.UpdateVisitPlanUseCase;

class UpdateVisitPlanUseCaseTest {

    private IVisitPlanRepository visitPlanRepository;
    private IHealthCareProfRepository healthCareProfRepository;
    private IMedicalSalesRepRepository medicalSalesRepRepository;
    private VisitPlanMapper mapper;
    private UpdateVisitPlanUseCase useCase;

    @BeforeEach
    void setUp() {
        visitPlanRepository = mock(IVisitPlanRepository.class);
        healthCareProfRepository = mock(IHealthCareProfRepository.class);
        medicalSalesRepRepository = mock(IMedicalSalesRepRepository.class);
        mapper = mock(VisitPlanMapper.class);
        useCase = new UpdateVisitPlanUseCase(visitPlanRepository, healthCareProfRepository, medicalSalesRepRepository, mapper);
    }

    // ── Happy path ────────────────────────────────────────────────────────────

    @Test
    void shouldUpdateVisitPlanWithTodayDateTime() throws DomainException {
        LocalDateTime today = LocalDateTime.now().withHour(10).withMinute(0).withSecond(0).withNano(0);
        UpdateVisitPlanInputDTO input = validInput(UUID.randomUUID().toString(), today);

        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);
        VisitPlan existing = mock(VisitPlan.class);

        when(visitPlanRepository.search(any(VisitId.class))).thenReturn(Optional.of(existing));
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
    void shouldUpdateVisitPlanWithFutureDateTime() throws DomainException {
        LocalDateTime future = LocalDateTime.now().plusWeeks(2);
        UpdateVisitPlanInputDTO input = validInput(UUID.randomUUID().toString(), future);

        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);
        VisitPlan existing = mock(VisitPlan.class);

        when(visitPlanRepository.search(any(VisitId.class))).thenReturn(Optional.of(existing));
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
        UpdateVisitPlanInputDTO input = validInput(UUID.randomUUID().toString(), yesterday);

        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);
        VisitPlan existing = mock(VisitPlan.class);

        when(visitPlanRepository.search(any(VisitId.class))).thenReturn(Optional.of(existing));
        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(msr));

        assertThrows(BusinessValidationException.class, () -> useCase.execute(input));
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenVisitDateTimeIsInThePast() {
        LocalDateTime pastDate = LocalDateTime.now().minusYears(1);
        UpdateVisitPlanInputDTO input = validInput(UUID.randomUUID().toString(), pastDate);

        HealthCareProf hcp = mock(HealthCareProf.class);
        MedicalSalesRep msr = mock(MedicalSalesRep.class);
        VisitPlan existing = mock(VisitPlan.class);

        when(visitPlanRepository.search(any(VisitId.class))).thenReturn(Optional.of(existing));
        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(msr));

        BusinessValidationException ex = assertThrows(BusinessValidationException.class,
                () -> useCase.execute(input));

        assertEquals("Visit date/time cannot be in the past.", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenVisitDateTimeIsNull() {
        UpdateVisitPlanInputDTO input = new UpdateVisitPlanInputDTO(
            UUID.randomUUID().toString(),
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
    void shouldThrowWhenIdIsNull() {
        UpdateVisitPlanInputDTO input = new UpdateVisitPlanInputDTO(
            null,
            LocalDateTime.now().plusDays(1),
            UUID.randomUUID().toString(),
            "comments",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit plan id cannot be null or empty", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenHealthCareProfIdIsNull() {
        UpdateVisitPlanInputDTO input = new UpdateVisitPlanInputDTO(
            UUID.randomUUID().toString(),
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
        UpdateVisitPlanInputDTO input = new UpdateVisitPlanInputDTO(
            UUID.randomUUID().toString(),
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
        UpdateVisitPlanInputDTO input = new UpdateVisitPlanInputDTO(
            UUID.randomUUID().toString(),
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
    void shouldThrowWhenVisitPlanNotFound() {
        UpdateVisitPlanInputDTO input = validInput(UUID.randomUUID().toString(), LocalDateTime.now().plusDays(1));

        when(visitPlanRepository.search(any(VisitId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit plan not found.", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenHealthCareProfNotFound() {
        UpdateVisitPlanInputDTO input = validInput(UUID.randomUUID().toString(), LocalDateTime.now().plusDays(1));
        VisitPlan existing = mock(VisitPlan.class);

        when(visitPlanRepository.search(any(VisitId.class))).thenReturn(Optional.of(existing));
        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Health Care Professional not found", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    @Test
    void shouldThrowWhenMedicalSalesRepNotFound() {
        UpdateVisitPlanInputDTO input = validInput(UUID.randomUUID().toString(), LocalDateTime.now().plusDays(1));
        VisitPlan existing = mock(VisitPlan.class);
        HealthCareProf hcp = mock(HealthCareProf.class);

        when(visitPlanRepository.search(any(VisitId.class))).thenReturn(Optional.of(existing));
        when(healthCareProfRepository.findById(any(HealthCareProfId.class))).thenReturn(Optional.of(hcp));
        when(medicalSalesRepRepository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Medical Sales Representative not found", ex.getMessage());
        verify(visitPlanRepository, never()).save(any(VisitPlan.class));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private UpdateVisitPlanInputDTO validInput(String id, LocalDateTime dateTime) {
        return new UpdateVisitPlanInputDTO(
            id,
            dateTime,
            UUID.randomUUID().toString(),
            "visit comments",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    private VisitPlanOutputDTO outputDTO(LocalDateTime dateTime, UpdateVisitPlanInputDTO input) {
        return new VisitPlanOutputDTO(
            input.id(),
            dateTime,
            input.healthCareProfId(),
            input.visitComments(),
            input.visitSiteId(),
            input.medicalSalesRepId()
        );
    }
}
