package com.das.cleanddd.domain.medicalSalesRepresentative.usecases.services;

import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentative;
import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentativeFactory;
import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentativeRepository;
import com.das.cleanddd.domain.medicalSalesRepresentative.usecases.dtos.CreateMedicalRepresentativeInputDTO;
import com.das.cleanddd.domain.medicalSalesRepresentative.usecases.dtos.MedicalSalesRepresentativeMapper;
import com.das.cleanddd.domain.medicalSalesRepresentative.usecases.dtos.MedicalSalesRepresentativeOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public final class CreateMedicalSalesRepresentativeUseCase implements UseCase<CreateMedicalRepresentativeInputDTO, MedicalSalesRepresentativeOutputDTO> {

    //private final CustomerDataAccess customerDataAccess;
    private final MedicalSalesRepresentativeRepository repository; 
    private final MedicalSalesRepresentativeFactory factory;
    private final MedicalSalesRepresentativeMapper mapper;
    
    public CreateMedicalSalesRepresentativeUseCase(MedicalSalesRepresentativeRepository repository
        , MedicalSalesRepresentativeFactory factory
        , MedicalSalesRepresentativeMapper mapper
        ) {
        this.repository = repository;
        this.factory = factory;
        this.mapper = mapper;   
    }

    public void create(String name, String surname, String email, Boolean isActive) {
        MedicalSalesRepresentative medicalSalesRepresentative = MedicalSalesRepresentative.create(name, surname, email, isActive);

        repository.save(medicalSalesRepresentative);
    }

    @Override
    public MedicalSalesRepresentativeOutputDTO execute(CreateMedicalRepresentativeInputDTO inputDTO)
            throws DomainException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
