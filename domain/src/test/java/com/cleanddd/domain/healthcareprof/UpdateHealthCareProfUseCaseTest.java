package com.cleanddd.domain.healthcareprof;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfFactory;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfRepository;
import com.das.cleanddd.domain.healthcareprof.entities.Specialty;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfMapper;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfOutputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.UpdateHealthCareProfInputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.services.UpdateHealthCareProfUseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class UpdateHealthCareProfUseCaseTest {

    private HealthCareProfRepository repository;
    private HealthCareProfFactory factory;
    private HealthCareProfMapper mapper;
    private UpdateHealthCareProfUseCase useCase;

    @Captor
    private ArgumentCaptor<List<Specialty>> specialtiesCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = mock(HealthCareProfRepository.class);
        factory = mock(HealthCareProfFactory.class);
        mapper = mock(HealthCareProfMapper.class);
        useCase = new UpdateHealthCareProfUseCase(repository, factory, mapper);
    }

    @Test
    void shouldThrowWhenSpecialtiesIsNull() throws BusinessException {
        UpdateHealthCareProfInputDTO input = new UpdateHealthCareProfInputDTO(
            "38c72d5f-4ce4-43e8-bb3a-cf49759cda17",
            "ValidName",
            "ValidSurname",
            "valid@email.com",
            null
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));

        assertEquals("Specialties cannot be null or empty", ex.getMessage());
        verify(factory, never()).recreateExistingHealthCareProf(any(), any(), any(), any(), any(), anyList());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowWhenSpecialtiesIsEmpty() throws BusinessException {
        UpdateHealthCareProfInputDTO input = new UpdateHealthCareProfInputDTO(
            "38c72d5f-4ce4-43e8-bb3a-cf49759cda17",
            "ValidName",
            "ValidSurname",
            "valid@email.com",
            List.of()
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));

        assertEquals("Specialties cannot be null or empty", ex.getMessage());
        verify(factory, never()).recreateExistingHealthCareProf(any(), any(), any(), any(), any(), anyList());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowWhenSpecialtyCodeIsInvalid() throws BusinessException {
        UpdateHealthCareProfInputDTO input = new UpdateHealthCareProfInputDTO(
            "38c72d5f-4ce4-43e8-bb3a-cf49759cda17",
            "ValidName",
            "ValidSurname",
            "valid@email.com",
            List.of("INVALID")
        );

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));

        assertEquals("Invalid specialty code: INVALID", ex.getMessage());
        verify(factory, never()).recreateExistingHealthCareProf(any(), any(), any(), any(), any(), anyList());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldMapPredefinedSpecialtyCodesWhenUpdating() throws DomainException, BusinessException {
        UpdateHealthCareProfInputDTO input = new UpdateHealthCareProfInputDTO(
            "38c72d5f-4ce4-43e8-bb3a-cf49759cda17",
            "ValidName",
            "ValidSurname",
            "valid@email.com",
            List.of("CARD", "PED")
        );

        HealthCareProf existing = mock(HealthCareProf.class);
        HealthCareProf recreated = mock(HealthCareProf.class);
        HealthCareProf activated = mock(HealthCareProf.class);

        when(existing.getEmail()).thenReturn(new HealthCareProfEmail("valid@email.com"));
        when(existing.isActive()).thenReturn(Boolean.TRUE);
        when(repository.findById(any())).thenReturn(Optional.of(existing));
        when(factory.recreateExistingHealthCareProf(any(), any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class), any(), anyList())).thenReturn(recreated);
        when(recreated.setActivate()).thenReturn(activated);

        HealthCareProfOutputDTO expected = new HealthCareProfOutputDTO(
            "38c72d5f-4ce4-43e8-bb3a-cf49759cda17",
            "ValidName",
            "ValidSurname",
            "valid@email.com",
            true,
            List.of("Cardiology", "Pediatrics")
        );
        when(mapper.outputFromEntity(activated)).thenReturn(expected);

        HealthCareProfOutputDTO output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(expected.id(), output.id());

        verify(factory).recreateExistingHealthCareProf(any(), any(), any(), any(), any(), specialtiesCaptor.capture());

        List<Specialty> mappedSpecialties = specialtiesCaptor.getValue();
        assertEquals(2, mappedSpecialties.size());
        assertEquals("CARD", mappedSpecialties.get(0).code());
        assertEquals("Cardiology", mappedSpecialties.get(0).name());
        assertEquals("PED", mappedSpecialties.get(1).code());
        assertEquals("Pediatrics", mappedSpecialties.get(1).name());
    }
}
