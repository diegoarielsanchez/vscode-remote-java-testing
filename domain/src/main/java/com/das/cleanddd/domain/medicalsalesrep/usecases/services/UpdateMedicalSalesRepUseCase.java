package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.UpdateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.BusinessValidationException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

@Service
public final class UpdateMedicalSalesRepUseCase implements UseCase<UpdateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> {

    @Autowired
    private final MedicalSalesRepRepository repository; 
    @Autowired
    private final MedicalSalesRepFactory factory;
    @Autowired
    private final MedicalSalesRepMapper mapper;

    public UpdateMedicalSalesRepUseCase(MedicalSalesRepRepository repository
        , MedicalSalesRepFactory factory
        , MedicalSalesRepMapper mapper
        ) {
        this.repository = repository;
        this.factory = factory;
        this.mapper = mapper;   
    }
    @Override
    public MedicalSalesRepOutputDTO execute(UpdateMedicalSalesRepInputDTO inputDTO)
            throws DomainException {
        // Validate input
        if (inputDTO == null) {
            throw new DomainException("Input DTO cannot be null");
        }
        if (inputDTO.name() == null || inputDTO.name().isEmpty()) {
            throw new DomainException("Name cannot be null or empty");
        }
        if (inputDTO.surname() == null || inputDTO.surname().isEmpty()) {
            throw new DomainException("Surname cannot be null or empty");
        }
        if (inputDTO.email() == null || inputDTO.email().isEmpty()) {
            throw new DomainException("Email cannot be null or empty");
        }

        MedicalSalesRep medicalSalesRep;
        MedicalSalesRep medicalSalesRepActivateStatus;
        MedicalSalesRepName medicalSalesRepName = new MedicalSalesRepName(inputDTO.name());
        MedicalSalesRepName medicalSalesRepSurname = new MedicalSalesRepName(inputDTO.surname());
        MedicalSalesRepEmail medicalSalesRepEmail = new MedicalSalesRepEmail(inputDTO.email());
        //MedicalSalesRepActive medicalSalesRepActive = new MedicalSalesRepActive(inputDTO.active());
        MedicalSalesRepId id = new MedicalSalesRepId(inputDTO.id());
        try {
            // Create a new MedicalSalesRep object using the factory
            medicalSalesRep = factory.recreateExistingMedicalSalesRepresentative(id, medicalSalesRepName, medicalSalesRepSurname, medicalSalesRepEmail,null);
        } catch (BusinessException  e) {
            // TODO: handle exception
            throw new BusinessValidationException(e.getMessage());
        }
        // fetch existing MedicalSalesRep from the repository
        Optional<MedicalSalesRep> existingMedicalSalesRep = repository.findById(id);
        if (!existingMedicalSalesRep.isPresent()) {
            throw new DomainException("Medical Sales Representative not found.");
        }
        // Validate Unique Email
        if (!existingMedicalSalesRep.get().getEmail().equals(medicalSalesRepEmail)) {
            Optional<MedicalSalesRep> medicalSalesRepWithEmail = repository.findByEmail(medicalSalesRepEmail);
            if (medicalSalesRepWithEmail.isPresent()) {
                throw new DomainException("There is already a Medical Sales Representative with this email.");
            }
        }
        // keep the existing MedicalSalesRep active status
        if (Boolean.TRUE.equals(existingMedicalSalesRep.get().isActive())) {
            medicalSalesRepActivateStatus = medicalSalesRep.setActivate();
        } else {
            medicalSalesRepActivateStatus = medicalSalesRep.setDeactivate();
        }
        // Update the existing MedicalSalesRep with the new values
        repository.save(medicalSalesRepActivateStatus);
        // Convert response to output and return
        return mapper.outputFromEntity(medicalSalesRepActivateStatus);
    }
}
