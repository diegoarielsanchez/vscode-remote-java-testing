package com.cleanddd.domain.healthcareprof;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfOutputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfSpecialtyInputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.services.FindHealthCareProfBySpecialtyUseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class FindHealthCareProfBySpecialtyUseCaseTest {

    private IHealthCareProfRepository repository;
    private HealthCareProfMapper mapper;
    private FindHealthCareProfBySpecialtyUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(IHealthCareProfRepository.class);
        mapper = mock(HealthCareProfMapper.class);
        useCase = new FindHealthCareProfBySpecialtyUseCase(repository, mapper);
    }

    @Test
    void shouldThrowWhenInputIsNull() {
        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(null));
        assertEquals("Specialty code cannot be null or empty.", ex.getMessage());
        verify(repository, never()).findBySpecialty(anyString(), anyInt(), anyInt());
    }

    @Test
    void shouldThrowWhenSpecialtyCodeIsNull() {
        HealthCareProfSpecialtyInputDTO input = new HealthCareProfSpecialtyInputDTO(null, 1, 10);

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Specialty code cannot be null or empty.", ex.getMessage());
        verify(repository, never()).findBySpecialty(anyString(), anyInt(), anyInt());
    }

    @Test
    void shouldThrowWhenSpecialtyCodeIsBlank() {
        HealthCareProfSpecialtyInputDTO input = new HealthCareProfSpecialtyInputDTO("   ", 1, 10);

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("Specialty code cannot be null or empty.", ex.getMessage());
        verify(repository, never()).findBySpecialty(anyString(), anyInt(), anyInt());
    }

    @Test
    void shouldThrowWhenNoResultsFound() {
        HealthCareProfSpecialtyInputDTO input = new HealthCareProfSpecialtyInputDTO("CARD", 1, 10);

        when(repository.findBySpecialty("CARD", 1, 10)).thenReturn(List.of());

        DomainException ex = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals("No Health Care Professionals found for the given specialty.", ex.getMessage());
    }

    @Test
    void shouldReturnMappedResultsWhenFound() throws DomainException {
        HealthCareProfSpecialtyInputDTO input = new HealthCareProfSpecialtyInputDTO("CARD", 1, 10);

        HealthCareProf prof = new HealthCareProf(
            new HealthCareProfId(UUID.randomUUID().toString()),
            new HealthCareProfName("John"),
            new HealthCareProfName("Doe"),
            new HealthCareProfEmail("john.doe@example.com"),
            new HealthCareProfActive(true),
            List.of(new Specialty("CARD", "Cardiology"))
        );

        HealthCareProfOutputDTO outputDTO = new HealthCareProfOutputDTO(
            prof.id().value(), "John", "Doe", "john.doe@example.com", true, List.of("Cardiology")
        );

        when(repository.findBySpecialty("CARD", 1, 10)).thenReturn(List.of(prof));
        when(mapper.outputFromEntityList(List.of(prof))).thenReturn(List.of(outputDTO));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        assertEquals(outputDTO, result.get(0));
        verify(repository).findBySpecialty("CARD", 1, 10);
    }

    @Test
    void shouldNormalizeSpecialtyCodeToUpperCase() throws DomainException {
        HealthCareProfSpecialtyInputDTO input = new HealthCareProfSpecialtyInputDTO("card", 1, 10);

        HealthCareProf prof = new HealthCareProf(
            new HealthCareProfId(UUID.randomUUID().toString()),
            new HealthCareProfName("Jane"),
            new HealthCareProfName("Smith"),
            new HealthCareProfEmail("jane.smith@example.com"),
            new HealthCareProfActive(true),
            List.of(new Specialty("CARD", "Cardiology"))
        );

        HealthCareProfOutputDTO outputDTO = new HealthCareProfOutputDTO(
            prof.id().value(), "Jane", "Smith", "jane.smith@example.com", true, List.of("Cardiology")
        );

        when(repository.findBySpecialty("CARD", 1, 10)).thenReturn(List.of(prof));
        when(mapper.outputFromEntityList(List.of(prof))).thenReturn(List.of(outputDTO));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        verify(repository).findBySpecialty("CARD", 1, 10);
    }

    @Test
    void shouldUseDefaultPaginationWhenValuesAreInvalid() throws DomainException {
        HealthCareProfSpecialtyInputDTO input = new HealthCareProfSpecialtyInputDTO("NEUR", 0, -5);

        HealthCareProf prof = new HealthCareProf(
            new HealthCareProfId(UUID.randomUUID().toString()),
            new HealthCareProfName("Alice"),
            new HealthCareProfName("Brown"),
            new HealthCareProfEmail("alice.brown@example.com"),
            new HealthCareProfActive(true),
            List.of(new Specialty("NEUR", "Neurology"))
        );

        HealthCareProfOutputDTO outputDTO = new HealthCareProfOutputDTO(
            prof.id().value(), "Alice", "Brown", "alice.brown@example.com", true, List.of("Neurology")
        );

        when(repository.findBySpecialty("NEUR", 1, 10)).thenReturn(List.of(prof));
        when(mapper.outputFromEntityList(List.of(prof))).thenReturn(List.of(outputDTO));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(1, result.size());
        verify(repository).findBySpecialty("NEUR", 1, 10);
    }

    @Test
    void shouldReturnMultipleResultsForSameSpecialty() throws DomainException {
        HealthCareProfSpecialtyInputDTO input = new HealthCareProfSpecialtyInputDTO("PED", 1, 10);

        HealthCareProf prof1 = new HealthCareProf(
            new HealthCareProfId(UUID.randomUUID().toString()),
            new HealthCareProfName("Carlos"),
            new HealthCareProfName("Lopez"),
            new HealthCareProfEmail("carlos.lopez@example.com"),
            new HealthCareProfActive(true),
            List.of(new Specialty("PED", "Pediatrics"))
        );
        HealthCareProf prof2 = new HealthCareProf(
            new HealthCareProfId(UUID.randomUUID().toString()),
            new HealthCareProfName("Maria"),
            new HealthCareProfName("Garcia"),
            new HealthCareProfEmail("maria.garcia@example.com"),
            new HealthCareProfActive(true),
            List.of(new Specialty("PED", "Pediatrics"))
        );

        HealthCareProfOutputDTO out1 = new HealthCareProfOutputDTO(
            prof1.id().value(), "Carlos", "Lopez", "carlos.lopez@example.com", true, List.of("Pediatrics")
        );
        HealthCareProfOutputDTO out2 = new HealthCareProfOutputDTO(
            prof2.id().value(), "Maria", "Garcia", "maria.garcia@example.com", true, List.of("Pediatrics")
        );

        when(repository.findBySpecialty("PED", 1, 10)).thenReturn(List.of(prof1, prof2));
        when(mapper.outputFromEntityList(List.of(prof1, prof2))).thenReturn(List.of(out1, out2));

        List<HealthCareProfOutputDTO> result = useCase.execute(input);

        assertEquals(2, result.size());
        verify(repository).findBySpecialty("PED", 1, 10);
    }
}
