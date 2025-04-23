package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.BusinessValidationException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

//@RequiredArgsConstructor
@Service
public final class CreateMedicalSalesRepUseCase implements UseCase<CreateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> {

    //private final CustomerDataAccess customerDataAccess;
    @Autowired
    private final MedicalSalesRepRepository repository; 
    @Autowired
    private final MedicalSalesRepFactory factory;
    @Autowired
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
        MedicalSalesRepName medicalSalesRepName = new MedicalSalesRepName(inputDTO.name());
        MedicalSalesRepName medicalSalesRepSurname = new MedicalSalesRepName(inputDTO.surname());
        MedicalSalesRepEmail medicalSalesRepEmail = new MedicalSalesRepEmail(inputDTO.email());
        //MedicalSalesRepId medicalSalesRepId = new MedicalSalesRepId(inputDTO.id());
        

        try {
            // Create a new MedicalSalesRep object using the factory
            medicalSalesRep = factory.createMedicalSalesRep(medicalSalesRepName, medicalSalesRepSurname, medicalSalesRepEmail);

        } catch (BusinessException  e) {
            // TODO: handle exception
            throw new BusinessValidationException(e.getMessage());
        }

        // Validate Unique Email
        //Optional<MedicalSalesRep> medicalSalesRepWithEmail = repository.findByEmail(inputDTO.email());
        Optional<MedicalSalesRep> medicalSalesRepWithEmail = repository.findByEmail(medicalSalesRepEmail);
        if(medicalSalesRepWithEmail.isPresent()) {
        throw new DomainException("There is already a Medical Sales Representative with this email.");
        }
        // Create
        repository.save(medicalSalesRep);

        // Convert response to output and return
        return mapper.outputFromEntity(medicalSalesRep);

    }

}
