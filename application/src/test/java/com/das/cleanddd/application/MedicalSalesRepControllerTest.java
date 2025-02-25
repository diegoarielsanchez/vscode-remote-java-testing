package com.das.cleanddd.application;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.MedicalSalesRepUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.UseCaseOnlyInput;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedicalSalesRepControllerTest {

    private MedicalSalesRepController medicalSalesRepController;
    private final MedicalSalesRepUseCaseFactory medicalSalesRepUseCaseFactory = mock(MedicalSalesRepUseCaseFactory.class);

    private final UseCase<?, ?> useCaseMock = mock(UseCase.class);
    private final UseCaseOnlyInput<?> useCaseOnlyInputMock = mock(UseCaseOnlyInput.class);
    //private final UUID validId = CustomerFixtures.VALID_ID;
    private MedicalSalesRepOutputDTO medicalSalesRepOutputMock = new MedicalSalesRepOutputDTO(MedicalSalesRepId.random(), null, null, null, null);

    @BeforeEach
    void beforeEach() {
        //medicalSalesRepController = new MedicalSalesRepController(medicalSalesRepUseCaseFactory);
        doReturn(useCaseMock).when(medicalSalesRepUseCaseFactory).getCreateMedicalSalesRepUseCase();
    }	

    //@Test


}
