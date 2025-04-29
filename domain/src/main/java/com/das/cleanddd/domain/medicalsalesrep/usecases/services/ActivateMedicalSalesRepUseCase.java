package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepIDDto;
import com.das.cleanddd.domain.shared.UseCaseOnlyInput;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public class ActivateMedicalSalesRepUseCase implements UseCaseOnlyInput<MedicalSalesRepIDDto> {
    
    //private final MedicalSalesRep medicalSalesRep;
        //private final CustomerDataAccess customerDataAccess;
    @Autowired
    private final MedicalSalesRepRepository repository; 


    public ActivateMedicalSalesRepUseCase(MedicalSalesRepRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public void execute(MedicalSalesRepIDDto inputDTO) throws DomainException {
        
        if(inputDTO.medicalSalesRepId()==null) {
            throw new DomainException("Medical Sales Representative Id is required.");
          }
        MedicalSalesRepId medicalSalesRepId = new MedicalSalesRepId(inputDTO.medicalSalesRepId());
        Optional<MedicalSalesRep> medicalSalesRep = repository.search(medicalSalesRepId);
        if(!medicalSalesRep.isPresent()) {
            throw new DomainException("Medical Sales Representative not found.");
        }
        if(Boolean.FALSE.equals(medicalSalesRep.get().isActive())) {
            medicalSalesRep.get().activate();
            repository.save(medicalSalesRep.get());
          }
    }
}
