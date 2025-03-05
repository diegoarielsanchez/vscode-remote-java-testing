package com.das.cleanddd.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.MedicalSalesRepUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

@RestController
public class MedicalSalesRepController {

    private final UseCase<CreateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> createMedicalSalesRepUseCase;
    
    public MedicalSalesRepController(MedicalSalesRepUseCaseFactory medicalSalesRepUseCaseFactory) {
        this.createMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getCreateMedicalSalesRepUseCase();
    }
     @GetMapping("/api/createmedicasSalesrep")
    public MedicalSalesRepOutputDTO createMedicalSalesRep(CreateMedicalSalesRepInputDTO inputDTO) throws DomainException{
        return createMedicalSalesRepUseCase.execute(inputDTO);
    }
}
