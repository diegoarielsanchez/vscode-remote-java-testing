package com.cleanddd.domain.healthcareprof;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfActive;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.healthcareprof.entities.IHealthCareProfRepository;
import com.das.cleanddd.domain.healthcareprof.entities.Specialty;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfMapper;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfNamesInputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfOutputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.services.FindHealthCareProfByNameUseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class FindHealthCareProfByNameUseCaseTest {

    private IHealthCareProfRepository repository;
    private HealthCareProfMapper mapper;
    private FindHealthCareProfByNameUseCase useCase;

    private HealthCareProf buildProf(String name, String surname) {
        return new HealthCareProf(
            new HealthCareProfId(UUID.randomUUID().toString()),
            new HealthCareProfName(name),
            new HealthCareProfName(surname),
            new HealthCareProfEmail(name.toLowerCase() + "@example.com"),
            new HealthCareProfActive(true),
            List.of(new Specialty("CARD", "Cardiology"))
        );
    }

    private HealthCareProfOutputDTO buildOutputDTO(HealthCareProf prof) {
        return new HealthCareProfOutputDTO(
            prof.id().value(),
            prof.getName().value(),
            prof.getSurname().value(),
            prof.getEmail().value(),
            prof.getActive().value(),
            List.of("Cardiology")
        );
    }

    @BeforeEach
    void setUp() {
        repository = mock(IHealthCareProfRepository.class);
        mapper = mock(HealthCareProfMapper.class);
        useCase = new FindHealthCareProfByNameUseCase(repository, mapper);
    }

    @Test
    void shouldThrowWhenNoResultsFoundBySurname() {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("", "Azurduy", 1, 4);

        when(repository.findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(4)))
            .thenReturn(List.of());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Health Care Professional not found.", ex.getMessage());
    }

    @Test
    void shouldReturnResultsMatchingBySurname() throws DomainException {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("", "Azurduy", 1, 4);

        HealthCareProf prof = buildProf("Juana", "Azurduy");
        HealthCareProfOutputDTO outputDTO = buildOutputDTO(prof);

        when(repository.findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(4)))
            .thenReturn(List.of(prof));
        when(mapper.outputFromEntityList(List.of(prof))).thenReturn(List.of(outputDTO));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        assertEquals(outputDTO, result.get(0));
        verify(repository).findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(4));
    }

    @Test
    void shouldReturnResultsMatchingByFirstName() throws DomainException {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("Juana", "", 1, 10);

        HealthCareProf prof = buildProf("Juana", "Azurduy");
        HealthCareProfOutputDTO outputDTO = buildOutputDTO(prof);

        when(repository.findByName(any(HealthCareProfName.class), isNull(), eq(1), eq(10)))
            .thenReturn(List.of(prof));
        when(mapper.outputFromEntityList(List.of(prof))).thenReturn(List.of(outputDTO));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        assertEquals(outputDTO, result.get(0));
        verify(repository).findByName(any(HealthCareProfName.class), isNull(), eq(1), eq(10));
    }

    @Test
    void shouldReturnResultsMatchingByBothNames() throws DomainException {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("Juana", "Azurduy", 1, 10);

        HealthCareProf prof = buildProf("Juana", "Azurduy");
        HealthCareProfOutputDTO outputDTO = buildOutputDTO(prof);

        when(repository.findByName(any(HealthCareProfName.class), any(HealthCareProfName.class), eq(1), eq(10)))
            .thenReturn(List.of(prof));
        when(mapper.outputFromEntityList(List.of(prof))).thenReturn(List.of(outputDTO));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        assertEquals(outputDTO, result.get(0));
    }

    @Test
    void shouldNormalizeNullInputToDefaults() {
        when(repository.searchAll()).thenReturn(List.of());

        assertThrows(DomainException.class, () -> useCase.execute(null));
        verify(repository).searchAll();
        verify(repository, never()).findByName(any(), any(), anyInt(), anyInt());
    }

    @Test
    void shouldNormalizeNegativePageToOne() {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("", "Azurduy", -1, 5);

        when(repository.findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(5)))
            .thenReturn(List.of());

        assertThrows(DomainException.class, () -> useCase.execute(input));
        verify(repository).findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(5));
    }

    @Test
    void shouldNormalizeNegativePageSizeToTen() {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("", "Azurduy", 1, -5);

        when(repository.findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(10)))
            .thenReturn(List.of());

        assertThrows(DomainException.class, () -> useCase.execute(input));
        verify(repository).findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(10));
    }

    @Test
    void shouldSearchAllAndThrowWhenBothNamesAreEmpty() {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("", "", 1, 10);

        when(repository.searchAll()).thenReturn(List.of());

        assertThrows(DomainException.class, () -> useCase.execute(input));
        verify(repository).searchAll();
        verify(repository, never()).findByName(any(), any(), anyInt(), anyInt());
    }

    @Test
    void shouldReturnMultipleResults() throws DomainException {
        HealthCareProfNamesInputDTO input = new HealthCareProfNamesInputDTO("", "Smith", 1, 10);

        HealthCareProf prof1 = buildProf("John", "Smith");
        HealthCareProf prof2 = buildProf("Jane", "Smith");
        HealthCareProfOutputDTO out1 = buildOutputDTO(prof1);
        HealthCareProfOutputDTO out2 = buildOutputDTO(prof2);

        when(repository.findByName(isNull(), any(HealthCareProfName.class), eq(1), eq(10)))
            .thenReturn(List.of(prof1, prof2));
        when(mapper.outputFromEntityList(List.of(prof1, prof2))).thenReturn(List.of(out1, out2));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(2, result.size());
    }
}
