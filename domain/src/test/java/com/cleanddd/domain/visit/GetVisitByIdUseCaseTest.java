package com.cleanddd.domain.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.das.cleanddd.domain.shared.exceptions.DomainException;
import com.das.cleanddd.domain.visit.IVisitRepository;
import com.das.cleanddd.domain.visit.entities.Visit;
import com.das.cleanddd.domain.visit.entities.VisitId;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitIDDto;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitMapper;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitOutputDTO;
import com.das.cleanddd.domain.visit.usecases.services.GetVisitByIdUseCase;

class GetVisitByIdUseCaseTest {

    private IVisitRepository repository;
    private VisitMapper mapper;
    private GetVisitByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(IVisitRepository.class);
        mapper = mock(VisitMapper.class);
        useCase = new GetVisitByIdUseCase(repository, mapper);
    }

    @Test
    void shouldReturnVisitWhenFound() throws DomainException {
        String id = UUID.randomUUID().toString();
        VisitIDDto input = new VisitIDDto(id);
        Visit visit = mock(Visit.class);
        VisitOutputDTO expected = new VisitOutputDTO(id, null, null, null, null, null);

        when(repository.search(any(VisitId.class))).thenReturn(Optional.of(visit));
        when(mapper.outputFromEntity(visit)).thenReturn(expected);

        VisitOutputDTO result = useCase.execute(input);
        assertEquals(expected, result);
    }

    @Test
    void shouldThrowWhenIdIsNull() {
        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(new VisitIDDto(null)));
        assertEquals("Visit Id is required.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenVisitNotFound() {
        VisitIDDto input = new VisitIDDto(UUID.randomUUID().toString());
        when(repository.search(any(VisitId.class))).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Visit not found.", ex.getMessage());
    }
}
