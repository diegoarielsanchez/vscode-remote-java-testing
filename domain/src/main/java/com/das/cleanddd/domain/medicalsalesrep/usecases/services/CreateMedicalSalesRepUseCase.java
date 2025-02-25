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

//@RequiredArgsConstructor
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

    @Override
    public MedicalSalesRepOutputDTO execute(CreateMedicalSalesRepInputDTO inputDTO)
            throws DomainException {

        MedicalSalesRep medicalSalesRep;
        try {
            medicalSalesRep = factory.createMedicalSalesRep(inputDTO.name(), inputDTO.surname(), inputDTO.email());
        } catch (BusinessException  e) {
            // TODO: handle exception
            throw new BusinessValidationException(e.getMessage());
        }

        // Validate Unique Email
        Optional<MedicalSalesRep> medicalSalesRepWithEmail = repository.findByEmail(inputDTO.email());
        if(medicalSalesRepWithEmail.isPresent()) {
        throw new DomainException("There is already a Medical Sales Representative with this email.");
        }
        // Create
        repository.save(medicalSalesRep);

        // Convert response to output and return
        return mapper.outputFromEntity(medicalSalesRep);

    }

}
