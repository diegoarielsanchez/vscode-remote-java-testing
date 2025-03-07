package com.das.cleanddd.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.MedicalSalesRepUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

@RestController
public class MedicalSalesRepController {
    @Autowired
    private final UseCase<CreateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> createMedicalSalesRepUseCase;
    
    public MedicalSalesRepController(MedicalSalesRepUseCaseFactory medicalSalesRepUseCaseFactory) {
        this.createMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getCreateMedicalSalesRepUseCase();
    }
     @GetMapping("/api/createmedicasSalesrep")
    public MedicalSalesRepOutputDTO createMedicalSalesRep(CreateMedicalSalesRepInputDTO inputDTO) throws DomainException{

        if (inputDTO == null) {
            inputDTO = new CreateMedicalSalesRepInputDTO("2025-03-07T09:58:06.279+00:00", "Manuel", "Belgrano", "manuel.belgrano@argentina.ar");
            //throw new DomainException("InputDTO is null");
        }
        return createMedicalSalesRepUseCase.execute(inputDTO);
    }
}
