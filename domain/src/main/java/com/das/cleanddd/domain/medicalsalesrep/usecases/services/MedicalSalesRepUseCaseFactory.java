package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;

public class MedicalSalesRepUseCaseFactory {

    private final MedicalSalesRepRepository medicalSalesRepRepository;
    private final MedicalSalesRepFactory medicalSalesRepFactory = new MedicalSalesRepFactory();
    private final MedicalSalesRepMapper medicalSalesRepMapper = new MedicalSalesRepMapper();
    
    private final CreateMedicalSalesRepUseCase createMedicalSalesRepUseCase;

    public MedicalSalesRepUseCaseFactory(MedicalSalesRepRepository medicalSalesRepRepository) {

        this.medicalSalesRepRepository = medicalSalesRepRepository;
        this.createMedicalSalesRepUseCase = new CreateMedicalSalesRepUseCase(this.medicalSalesRepRepository, this.medicalSalesRepFactory, this.medicalSalesRepMapper);
    }

    public UseCase<CreateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> getCreateMedicalSalesRepUseCase() {
        return createMedicalSalesRepUseCase;
    }
}
