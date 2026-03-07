package com.cleanddd.domain.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.shared.exceptions.DomainException;
import com.das.cleanddd.domain.visit.IVisitRepository;
import com.das.cleanddd.domain.visit.entities.Visit;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitMapper;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitOutputDTO;
import com.das.cleanddd.domain.visit.usecases.services.ListVisitsUseCase;

class ListVisitsUseCaseTest {

    private IVisitRepository repository;
    private VisitMapper mapper;
    private ListVisitsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(IVisitRepository.class);
        mapper = mock(VisitMapper.class);
        useCase = new ListVisitsUseCase(repository, mapper);
    }

    @Test
    void shouldReturnListOfVisits() throws DomainException {
        List<Visit> visits = List.of(mock(Visit.class));
        List<VisitOutputDTO> expected = List.of(new VisitOutputDTO("id", null, null, null, null, null));

        when(repository.searchAll()).thenReturn(visits);
        when(mapper.outputFromEntityList(visits)).thenReturn(expected);

        List<VisitOutputDTO> result = useCase.execute();
        assertEquals(expected, result);
    }

    @Test
    void shouldThrowWhenNoVisitsFound() {
        when(repository.searchAll()).thenReturn(List.of());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute());
        assertEquals("Visit not found.", ex.getMessage());
    }
}
