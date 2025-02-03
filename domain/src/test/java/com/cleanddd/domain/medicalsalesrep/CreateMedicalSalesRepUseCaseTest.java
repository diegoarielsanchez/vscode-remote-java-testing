package com.cleanddd.domain.medicalsalesrep;

import static org.mockito.Mockito.mock;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;

public class CreateMedicalSalesRepUseCaseTest {

    private final MedicalSalesRepRepository medicalSalesRepDataAccessMock = mock(MedicalSalesRepRepository.class);
    private final MedicalSalesRepFactory medicalSalesRepFactoryMock = mock(MedicalSalesRepFactory.class);
    private final MedicalSalesRepMapper medicalSalesRepMapperMock = mock(MedicalSalesRepMapper.class);
    //private final CreateCustomerUseCase createCustomerUseCase = new CreateCustomerUseCase(customerDataAccessMock,customerFactoryMock,customerMapperMock);

}
