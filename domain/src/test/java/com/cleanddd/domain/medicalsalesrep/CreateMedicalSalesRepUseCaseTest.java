package com.cleanddd.domain.medicalsalesrep;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

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

public class CreateMedicalSalesRepUseCaseTest {

    private final MedicalSalesRepRepository medicalSalesRepDataAccessMock = mock(MedicalSalesRepRepository.class);
    private final MedicalSalesRepFactory medicalSalesRepFactoryMock = mock(MedicalSalesRepFactory.class);
    private final MedicalSalesRepMapper medicalSalesRepMapperMock = mock(MedicalSalesRepMapper.class);
    private final CreateMedicalSalesRepUseCase createmedicalSalesRepUseCase = new CreateMedicalSalesRepUseCase(medicalSalesRepDataAccessMock,medicalSalesRepFactoryMock,medicalSalesRepMapperMock);

    private final MedicalSalesRep medicalSalesRepMock = mock(MedicalSalesRep.class, Mockito.RETURNS_DEEP_STUBS);
     //private final UUID validId = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
    private final MedicalSalesRepId validId = MedicalSalesRepIdMother.random();
    private final MedicalSalesRepName validName = MedicalSalesRepNameMother.random();
    private final MedicalSalesRepName validSurname = MedicalSalesRepNameMother.random();
    private final MedicalSalesRepEmail validEmail = MedicalSalesRepEmailMother.random();
    //private final MedicalSalesRepActive validActive = MedicalSalesRepActiveMother.random();
    private CreateMedicalSalesRepInputDTO validInputDto = new CreateMedicalSalesRepInputDTO(validId, validName, validSurname, validEmail);
    private MedicalSalesRepOutputDTO validOutputDto = new MedicalSalesRepOutputDTO(validId, validName, validSurname, validEmail);

    void prepareStubs() throws BusinessException {
        //when(medicalSalesRepFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
        doReturn(medicalSalesRepMock).when(medicalSalesRepFactoryMock).createMedicalSalesRepresentative(validId, validName, validSurname, validEmail)(anyString(), anyString(), any());
        when(medicalSalesRepMapperMock.outputFromEntity(any())).thenReturn(validOutputDto);
  }

    }
