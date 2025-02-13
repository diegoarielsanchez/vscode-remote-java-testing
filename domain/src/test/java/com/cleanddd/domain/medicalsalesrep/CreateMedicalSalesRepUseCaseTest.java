package com.cleanddd.domain.medicalsalesrep;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepActive;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.CreateMedicalSalesRepUseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.BusinessValidationException;
import com.das.cleanddd.domain.shared.exceptions.DataAccessException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public class CreateMedicalSalesRepUseCaseTest {

    private final MedicalSalesRepRepository medicalSalesRepDataAccessMock = mock(MedicalSalesRepRepository.class);
    private final MedicalSalesRepFactory medicalSalesRepFactoryMock = mock(MedicalSalesRepFactory.class);
    private final MedicalSalesRepMapper medicalSalesRepMapperMock = mock(MedicalSalesRepMapper.class);
    private final CreateMedicalSalesRepUseCase createMedicalSalesRepUseCase = new CreateMedicalSalesRepUseCase(medicalSalesRepDataAccessMock,medicalSalesRepFactoryMock,medicalSalesRepMapperMock);

    private final MedicalSalesRep medicalSalesRepMock = mock(MedicalSalesRep.class, Mockito.RETURNS_DEEP_STUBS);
     //private final UUID validId = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
    private final MedicalSalesRepId validId = MedicalSalesRepIdMother.random();
    private final MedicalSalesRepName validName = MedicalSalesRepNameMother.random();
    private final MedicalSalesRepName validSurname = MedicalSalesRepNameMother.random();
    private final MedicalSalesRepEmail validEmail = MedicalSalesRepEmailMother.random();
    private final MedicalSalesRepActive validActive = MedicalSalesRepActiveMother.create(false);
    private CreateMedicalSalesRepInputDTO validInputDTO = new CreateMedicalSalesRepInputDTO(validId, validName, validSurname, validEmail);

    private MedicalSalesRepOutputDTO validOutputDto = new MedicalSalesRepOutputDTO(validId, validName, validSurname, validEmail, validActive);

    void prepareStubs() throws BusinessException {
        //when(medicalSalesRepFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
        doReturn(medicalSalesRepMock).when(medicalSalesRepFactoryMock).createMedicalSalesRep(MedicalSalesRepNameMother.random(),MedicalSalesRepNameMother.random(),MedicalSalesRepEmailMother.random());
        when(medicalSalesRepMapperMock.outputFromEntity(any())).thenReturn(validOutputDto);
    }
    @Test
    void shouldCallFactoriesAndDataAccess() throws BusinessException, DomainException {
      prepareStubs();
      createMedicalSalesRepUseCase.execute(validInputDTO);
      verify(medicalSalesRepFactoryMock, times(1)).createMedicalSalesRep(validInputDTO.name(), validInputDTO.surname(), validInputDTO.email());
      verify(medicalSalesRepDataAccessMock, times(1)).save(any());
    }

    @Test
    void shouldReturnMedicalSalesRep() throws DomainException, BusinessException {
      prepareStubs();
      MedicalSalesRepOutputDTO outputDTO = createMedicalSalesRepUseCase.execute(validInputDTO);
      assertNotNull(outputDTO);
  }
  @Nested
  class CreatingMedicalSalesRepShouldThrow {

    @Test
    void whenEntitiesThrows() throws BusinessException {
      // Test throw for Address
      //when(medicalSalesRepFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenThrow(new BusinessException(""));
      //assertThrows(BusinessValidationException.class,() -> createMedicalSalesRepUseCase.execute(validInputDTO));
      
      // Test throw for MedicalSalesRep
      reset(medicalSalesRepFactoryMock);
      //when(medicalSalesRepFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
      when(medicalSalesRepFactoryMock.createMedicalSalesRep(MedicalSalesRepNameMother.random(),MedicalSalesRepNameMother.random(),MedicalSalesRepEmailMother.random())).thenThrow(new BusinessException(""));
      assertThrows(BusinessValidationException.class,() -> createMedicalSalesRepUseCase.execute(validInputDTO));
    }

    @Test
    void withoutUniqueEmail() throws BusinessException, DataAccessException {
      prepareStubs();
      doReturn(Optional.of(medicalSalesRepMock)).when(medicalSalesRepDataAccessMock).findByEmail(any());

      DomainException thrown = assertThrows(DomainException.class,() -> createMedicalSalesRepUseCase.execute(validInputDTO));
      assertTrue(thrown.getMessage().contains("There is already a MedicalSalesRep with this email."));
    }

    @Test
    void whenDataAccessThrows() throws BusinessException, DataAccessException {
      prepareStubs();
      doThrow(new DataAccessException("Test cause")).when(medicalSalesRepDataAccessMock).save(any());
      assertThrows(DataAccessException.class,() -> createMedicalSalesRepUseCase.execute(validInputDTO));
    }

  }



}
