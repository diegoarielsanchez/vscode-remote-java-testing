package com.cleanddd.domain.medicalsalesrep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.medicalsalesrep.entities.IMedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepActive;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepNamesInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.FindMedicalSalesRepByNameUseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class FindMedicalSalesRepByNameUseCaseTest {

    private IMedicalSalesRepRepository repository;
    private MedicalSalesRepMapper mapper;
    private FindMedicalSalesRepByNameUseCase useCase;

    private MedicalSalesRep buildRep(String name, String surname) {
        return new MedicalSalesRep(
            new MedicalSalesRepId(UUID.randomUUID().toString()),
            new MedicalSalesRepName(name),
            new MedicalSalesRepName(surname),
            new MedicalSalesRepEmail(name.toLowerCase() + "@example.com"),
            new MedicalSalesRepActive(true)
        );
    }

    private MedicalSalesRepOutputDTO buildOutputDTO(MedicalSalesRep rep) {
        return new MedicalSalesRepOutputDTO(
            rep.id().value(),
            rep.getName().value(),
            rep.getSurname().value(),
            rep.getEmail().value(),
            rep.getActive().value()
        );
    }

    @BeforeEach
    void setUp() {
        repository = mock(IMedicalSalesRepRepository.class);
        mapper = mock(MedicalSalesRepMapper.class);
        useCase = new FindMedicalSalesRepByNameUseCase(repository, mapper);
    }

    @Test
    void shouldThrowWhenNoResultsFoundBySurname() {
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("", "Smith", 1, 4);

        when(repository.findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(4)))
            .thenReturn(List.of());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Medical Sales Representative not found.", ex.getMessage());
    }

    @Test
    void shouldReturnResultsMatchingBySurname() throws DomainException {
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("", "Smith", 1, 4);

        MedicalSalesRep rep = buildRep("John", "Smith");
        MedicalSalesRepOutputDTO outputDTO = buildOutputDTO(rep);

        when(repository.findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(4)))
            .thenReturn(List.of(rep));
        when(mapper.outputFromEntityList(List.of(rep))).thenReturn(List.of(outputDTO));

        List<MedicalSalesRepOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        assertEquals(outputDTO, result.get(0));
        verify(repository).findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(4));
    }

    @Test
    void shouldReturnResultsMatchingByFirstName() throws DomainException {
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("John", "", 1, 10);

        MedicalSalesRep rep = buildRep("John", "Smith");
        MedicalSalesRepOutputDTO outputDTO = buildOutputDTO(rep);

        when(repository.findByName(any(MedicalSalesRepName.class), isNull(), eq(1), eq(10)))
            .thenReturn(List.of(rep));
        when(mapper.outputFromEntityList(List.of(rep))).thenReturn(List.of(outputDTO));

        List<MedicalSalesRepOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        assertEquals(outputDTO, result.get(0));
        verify(repository).findByName(any(MedicalSalesRepName.class), isNull(), eq(1), eq(10));
    }

    @Test
    void shouldReturnResultsMatchingByBothNames() throws DomainException {
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("John", "Smith", 1, 10);

        MedicalSalesRep rep = buildRep("John", "Smith");
        MedicalSalesRepOutputDTO outputDTO = buildOutputDTO(rep);

        when(repository.findByName(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), eq(1), eq(10)))
            .thenReturn(List.of(rep));
        when(mapper.outputFromEntityList(List.of(rep))).thenReturn(List.of(outputDTO));

        List<MedicalSalesRepOutputDTO> result = useCase.execute(input);

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
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("", "Smith", -1, 5);

        when(repository.findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(5)))
            .thenReturn(List.of());

        assertThrows(DomainException.class, () -> useCase.execute(input));
        verify(repository).findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(5));
    }

    @Test
    void shouldNormalizeNegativePageSizeToTen() {
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("", "Smith", 1, -5);

        when(repository.findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(10)))
            .thenReturn(List.of());

        assertThrows(DomainException.class, () -> useCase.execute(input));
        verify(repository).findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(10));
    }

    @Test
    void shouldSearchAllAndThrowWhenBothNamesAreEmpty() {
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("", "", 1, 10);

        when(repository.searchAll()).thenReturn(List.of());

        assertThrows(DomainException.class, () -> useCase.execute(input));
        verify(repository).searchAll();
        verify(repository, never()).findByName(any(), any(), anyInt(), anyInt());
    }

    @Test
    void shouldReturnMultipleResults() throws DomainException {
        MedicalSalesRepNamesInputDTO input = new MedicalSalesRepNamesInputDTO("", "Smith", 1, 10);

        MedicalSalesRep rep1 = buildRep("John", "Smith");
        MedicalSalesRep rep2 = buildRep("Jane", "Smith");
        MedicalSalesRepOutputDTO out1 = buildOutputDTO(rep1);
        MedicalSalesRepOutputDTO out2 = buildOutputDTO(rep2);

        when(repository.findByName(isNull(), any(MedicalSalesRepName.class), eq(1), eq(10)))
            .thenReturn(List.of(rep1, rep2));
        when(mapper.outputFromEntityList(List.of(rep1, rep2))).thenReturn(List.of(out1, out2));

        List<MedicalSalesRepOutputDTO> result = useCase.execute(input);

        assertEquals(2, result.size());
    }
}
