package com.das.cleanddd.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.MedicalSalesRepUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.UseCaseOnlyInput;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class MedicalSalesRepControllerTest {

    private MedicalSalesRepController medicalSalesRepController;
    private final MedicalSalesRepUseCaseFactory medicalSalesRepUseCaseFactoryMock = mock(MedicalSalesRepUseCaseFactory.class);
    
    private final UseCase<?, ?> useCaseMock = mock(UseCase.class);
    private final UseCaseOnlyInput<?> useCaseOnlyInputMock = mock(UseCaseOnlyInput.class);

    //private final MedicalSalesRepId validId = MedicalSalesRepIdMother.random();
    private final String validId = "e3119506-030a-4877-a219-389ef21118a4";
    //private final MedicalSalesRepName validName = MedicalSalesRepNameMother.random();
    private final String validName = "John";
    //private final MedicalSalesRepName validSurname = MedicalSalesRepNameMother.random();
    private final String validSurname = "Doe";
    //private final MedicalSalesRepEmail validEmail = MedicalSalesRepEmailMother.random();
    private final String validEmail = "jojn.doe@validdomain.com";
    //private final MedicalSalesRepActive validActive = MedicalSalesRepActiveMother.create(false);
    private final Boolean validActive = false;

    //private MedicalSalesRepOutputDTO medicalSalesRepOutputDTOMock =  mock(MedicalSalesRepOutputDTO.class);
    private final MedicalSalesRepOutputDTO medicalSalesRepOutputDTOMock =  new MedicalSalesRepOutputDTO(
        validId,
        validName,
        validSurname,
        validEmail,
        validActive
    );

    @BeforeEach
    void beforeEach() {
        doReturn(useCaseMock).when(medicalSalesRepUseCaseFactoryMock).getCreateMedicalSalesRepUseCase();
        // doReturn(useCaseMock).when(customerUseCaseFactoryMock).getUpdateCustomerUseCase();
        // doReturn(useCaseMock).when(customerUseCaseFactoryMock).getFindCustomerByNameUseCase();
        // doReturn(useCaseMock).when(customerUseCaseFactoryMock).getGetCustomerByIdUseCase();
        // doReturn(useCaseOnlyInputMock).when(customerUseCaseFactoryMock).getActivateCustomerUseCase();
        // doReturn(useCaseOnlyInputMock).when(customerUseCaseFactoryMock).getDeactivateCustomerUseCase();
        this.medicalSalesRepController = new MedicalSalesRepController(medicalSalesRepUseCaseFactoryMock);
  }
    @Test
  void shouldCallUseCasesCorrectlyCreate() throws DomainException {
    medicalSalesRepController.createMedicalSalesRep(null);
    verify(useCaseMock, times(1)).execute(any());
    }
}

