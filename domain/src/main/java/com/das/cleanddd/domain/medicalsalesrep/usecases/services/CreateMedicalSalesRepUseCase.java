package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import java.util.Optional;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.BusinessValidationException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public final class CreateMedicalSalesRepUseCase implements UseCase<CreateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> {

    //private final CustomerDataAccess customerDataAccess;
    private final MedicalSalesRepRepository repository; 
    private final MedicalSalesRepFactory factory;
    private final MedicalSalesRepMapper mapper;
    
    public CreateMedicalSalesRepUseCase(MedicalSalesRepRepository repository
        , MedicalSalesRepFactory factory
        , MedicalSalesRepMapper mapper
        ) {
        this.repository = repository;
        this.factory = factory;
        this.mapper = mapper;   
    }

/*     public void create(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email,MedicalSalesRepActive active) {

        MedicalSalesRep medicalSalesRepresentative = MedicalSalesRep.create(id, name, surname, email, active);
        repository.save(medicalSalesRepresentative);
        //eventBus.publish(medicalSalesRepresentative.pullDomainEvents());
    } */

    @Override
    public MedicalSalesRepOutputDTO execute(CreateMedicalSalesRepInputDTO inputDTO)
            throws DomainException {

        MedicalSalesRep medicalSalesRep;
        try {
            
            medicalSalesRep = factory.createMedicalSalesRep(
                //inputDTO.id()
                //, 
                inputDTO.name()
                , inputDTO.surname()
                , inputDTO.email()
                //, false
                );
                
        } catch (BusinessException  e) {
            // TODO: handle exception
            throw new BusinessValidationException(e.getMessage());
        }

/*         medicalSalesRep = MedicalSalesRep.create( 
            new MedicalSalesRepId(inputDTO.id())
            , new MedicalSalesRepName(inputDTO.name())
            , new MedicalSalesRepName(inputDTO.surname())
            , new MedicalSalesRepEmail(inputDTO.email())
            , new MedicalSalesRepActive(inputDTO.active())); */

        // Validate Unique Email
        Optional<MedicalSalesRep> medicalSalesRepWithEmail = repository.findByEmail(medicalSalesRep.email());
        if(medicalSalesRepWithEmail.isPresent()) {
        throw new DomainException("There is already a Medical Sales Representative with this email.");
        }
        // Create
        repository.save(medicalSalesRep);
        // Convert respose to output and return
        return mapper.outputFromEntity(medicalSalesRep);
        //throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
