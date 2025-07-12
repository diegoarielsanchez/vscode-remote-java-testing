package com.das.cleanddd.application;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.http.ResponseEntity;

import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.MedicalSalesRepUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.UseCaseOnlyInput;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class MedicalSalesRepControllerTest {

    private MedicalSalesRepController medicalSalesRepController;
    private final MedicalSalesRepUseCaseFactory medicalSalesRepUseCaseFactoryMock = mock(MedicalSalesRepUseCaseFactory.class);
    
    private final UseCase<?, ?> useCaseMock = mock(UseCase.class);
    private final UseCaseOnlyInput<?> useCaseOnlyInputMock = mock(UseCaseOnlyInput.class);

    private final String validId = "e3119506-030a-4877-a219-389ef21118a4";
    private final String validName = "John";
    private final String validSurname = "Doe";
    private final String validEmail = "jojn.doe@validdomain.com";
    private final Boolean validActive = false;
    
    private final MedicalSalesRepOutputDTO medicalSalesRepOutputDTOMock =  new MedicalSalesRepOutputDTO(validId, validName, validSurname, validEmail, validActive);

    @BeforeEach
    void beforeEach() {
        doReturn(useCaseMock).when(medicalSalesRepUseCaseFactoryMock).getCreateMedicalSalesRepUseCase();
        doReturn(useCaseMock).when(medicalSalesRepUseCaseFactoryMock).getUpdateMedicalSalesRepUseCase();
        doReturn(useCaseMock).when(medicalSalesRepUseCaseFactoryMock).findMedicalSalesRepByNameUseCase();
        doReturn(useCaseMock).when(medicalSalesRepUseCaseFactoryMock).getGetMedicalSalesRepByIdUseCase();
        doReturn(useCaseOnlyInputMock).when(medicalSalesRepUseCaseFactoryMock).getActivateMedicalSalesRepUseCase();
        doReturn(useCaseOnlyInputMock).when(medicalSalesRepUseCaseFactoryMock).getDeActivateMedicalSalesRepUseCase();
        this.medicalSalesRepController = new MedicalSalesRepController(medicalSalesRepUseCaseFactoryMock);
  }
  @Test
  void shouldCallUseCasesCorrectlyCreate() throws DomainException {
    medicalSalesRepController.createMedicalSalesRep(null);
    verify(useCaseMock, times(1)).execute(any());
    }
  @Test
  void shouldCallUseCasesCorrectlyUpdate() throws DomainException {
    medicalSalesRepController.updateMedicalSalesRep(null);
    verify(useCaseMock, times(1)).execute(any());
  }
  @Test
  void shouldCallUseCasesCorrectlyFind() throws DomainException {
    medicalSalesRepController.findMedicalSalesRepByName(null);
    verify(useCaseMock, times(1)).execute(any());
  }
  @Test
  void shouldCallUseCasesCorrectlyGet() throws DomainException {
    medicalSalesRepController.getMedicalSalesRepByID(null);
    verify(useCaseMock, times(1)).execute(any());
  }  
  @Test
  void shouldCallUseCasesCorrectlyActivate() throws DomainException {
    medicalSalesRepController.activateMedicalSalesRep(null);
    verify(useCaseOnlyInputMock, times(1)).execute(any());
  }
  @Test
  void shouldCallUseCasesCorrectlyDeactivate() throws DomainException {
    medicalSalesRepController.deactivateMedicalSalesRep(null);
    verify(useCaseOnlyInputMock, times(1)).execute(any());
  }  
  @Test
  void shouldCallUseCasesCorrectlyCreateActive() throws DomainException {
    doReturn(medicalSalesRepOutputDTOMock).when(useCaseMock).execute(any());

    medicalSalesRepController.createMedicalSalesRep(null);
    verify(useCaseMock, times(1)).execute(any());
    verify(useCaseOnlyInputMock, times(1)).execute(any());
  }

  @Test
  void shouldReturnMedicalSalesRepActivated() throws DomainException {
    doReturn(medicalSalesRepOutputDTOMock).when(useCaseMock).execute(any());
    ResponseEntity<Object> actual = medicalSalesRepController.createMedicalSalesRep(null);
    MedicalSalesRepOutputDTO body = (MedicalSalesRepOutputDTO) actual.getBody();
    assertTrue(body != null && body.active());
  }

}

