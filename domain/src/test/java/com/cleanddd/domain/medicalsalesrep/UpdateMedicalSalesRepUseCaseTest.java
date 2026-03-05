package com.cleanddd.domain.medicalsalesrep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.UpdateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.UpdateMedicalSalesRepUseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class UpdateMedicalSalesRepUseCaseTest {

    private MedicalSalesRepRepository repository;
    private MedicalSalesRepFactory factory;
    private MedicalSalesRepMapper mapper;
    private UpdateMedicalSalesRepUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(MedicalSalesRepRepository.class);
        factory = mock(MedicalSalesRepFactory.class);
        mapper = mock(MedicalSalesRepMapper.class);
        useCase = new UpdateMedicalSalesRepUseCase(repository, factory, mapper);
    }

    @Test
    void shouldThrowWhenInputDTOIsNull() throws BusinessException {
        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(null));

        assertEquals("Input DTO cannot be null", ex.getMessage());
        verify(factory, never()).recreateExistingMedicalSalesRepresentative(any(), any(), any(), any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowWhenMedicalSalesRepNotFound() throws BusinessException {
        UpdateMedicalSalesRepInputDTO inputDTO = new UpdateMedicalSalesRepInputDTO(
            "4e2f5c68-c5ed-4f17-8f3c-41a66f4252c5",
            "Diego",
            "Sanchez",
            "diego@example.com"
        );

        when(repository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(inputDTO));

        assertEquals("Medical Sales Representative not found.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowWhenEmailAlreadyExistsInAnotherRep() throws BusinessException {
        UpdateMedicalSalesRepInputDTO inputDTO = new UpdateMedicalSalesRepInputDTO(
            "4e2f5c68-c5ed-4f17-8f3c-41a66f4252c5",
            "Diego",
            "Sanchez",
            "new-email@example.com"
        );

        MedicalSalesRep existingRep = mock(MedicalSalesRep.class);
        MedicalSalesRep recreatedRep = mock(MedicalSalesRep.class);
        MedicalSalesRep repWithSameEmail = mock(MedicalSalesRep.class);

        when(existingRep.getEmail()).thenReturn(new MedicalSalesRepEmail("current-email@example.com"));
        when(repository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(existingRep));
        when(factory.recreateExistingMedicalSalesRepresentative(any(), any(), any(), any(), any())).thenReturn(recreatedRep);
        when(repository.findByEmail(any(MedicalSalesRepEmail.class))).thenReturn(Optional.of(repWithSameEmail));

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(inputDTO));

        assertEquals("There is already a Medical Sales Representative with this email.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldUpdateMedicalSalesRepAndKeepActiveStatus() throws DomainException, BusinessException {
        UpdateMedicalSalesRepInputDTO inputDTO = new UpdateMedicalSalesRepInputDTO(
            "4e2f5c68-c5ed-4f17-8f3c-41a66f4252c5",
            "Diego",
            "Sanchez",
            "diego@example.com"
        );

        MedicalSalesRep existingRep = mock(MedicalSalesRep.class);
        MedicalSalesRep recreatedRep = mock(MedicalSalesRep.class);
        MedicalSalesRep activatedRep = mock(MedicalSalesRep.class);

        when(existingRep.getEmail()).thenReturn(new MedicalSalesRepEmail("diego@example.com"));
        when(existingRep.isActive()).thenReturn(Boolean.TRUE);
        when(repository.findById(any(MedicalSalesRepId.class))).thenReturn(Optional.of(existingRep));

        when(factory.recreateExistingMedicalSalesRepresentative(any(), any(), any(), any(), any())).thenReturn(recreatedRep);
        when(recreatedRep.setActivate()).thenReturn(activatedRep);

        MedicalSalesRepOutputDTO expected = new MedicalSalesRepOutputDTO(
            "4e2f5c68-c5ed-4f17-8f3c-41a66f4252c5",
            "Diego",
            "Sanchez",
            "diego@example.com",
            true
        );
        when(mapper.outputFromEntity(activatedRep)).thenReturn(expected);

        MedicalSalesRepOutputDTO output = useCase.execute(inputDTO);

        assertNotNull(output);
        assertEquals(expected, output);
        verify(repository).save(eq(activatedRep));
        verify(mapper).outputFromEntity(eq(activatedRep));
    }
}
