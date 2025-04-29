package com.das.cleanddd.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepIDDto;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.MedicalSalesRepUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.UseCaseOnlyInput;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/v1/medicalsalesrep")
public class MedicalSalesRepController {
    @Autowired
    private final UseCase<CreateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> createMedicalSalesRepUseCase;
    private final UseCaseOnlyInput<MedicalSalesRepIDDto> activateMedicalSalesRepUseCase;
    private final UseCaseOnlyInput<MedicalSalesRepIDDto> deactivateMedicalSalesRepUseCase;
    
    public MedicalSalesRepController(MedicalSalesRepUseCaseFactory medicalSalesRepUseCaseFactory) {
        this.createMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getCreateMedicalSalesRepUseCase();
        this.activateMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getActivateMedicalSalesRepUseCase();
        this.deactivateMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getDeActivateMedicalSalesRepUseCase();
    }
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalSalesRepOutputDTO createMedicalSalesRep(@RequestBody CreateMedicalSalesRepInputDTO inputDTO) throws DomainException{
        return createMedicalSalesRepUseCase.execute(inputDTO);
    }

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateMedicalSalesRep(@RequestBody MedicalSalesRepIDDto inputDTO) throws DomainException{
        activateMedicalSalesRepUseCase.execute(inputDTO);
    }
    @PostMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateMedicalSalesRep(@RequestBody MedicalSalesRepIDDto inputDTO) throws DomainException{
        deactivateMedicalSalesRepUseCase.execute(inputDTO);
    }
    
}
