package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public final class CreateMedicalSalesRepUseCase implements UseCase<CreateMedicalRepInputDTO, MedicalSalesRepOutputDTO> {

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

    public void create(MedicalSalesRepId id, String name, String surname, String email, Boolean active) {
        MedicalSalesRep medicalSalesRepresentative = MedicalSalesRep.create(id, name, surname, email, active);

        repository.save(medicalSalesRepresentative);
    }

    @Override
    public MedicalSalesRepOutputDTO execute(CreateMedicalRepInputDTO inputDTO)
            throws DomainException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
