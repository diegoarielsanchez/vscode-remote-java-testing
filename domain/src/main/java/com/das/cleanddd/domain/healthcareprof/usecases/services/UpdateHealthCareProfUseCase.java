package com.das.cleanddd.domain.healthcareprof.usecases.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfFactory;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfRepository;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfMapper;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfOutputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.UpdateHealthCareProfInputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

@Service
public final class UpdateHealthCareProfUseCase implements UseCase<UpdateHealthCareProfInputDTO, HealthCareProfOutputDTO> {

    @Autowired
    private final HealthCareProfRepository repository; 
    @Autowired
    private final HealthCareProfFactory factory;
    @Autowired
    private final HealthCareProfMapper mapper;

    public UpdateHealthCareProfUseCase(HealthCareProfRepository repository
        , HealthCareProfFactory factory
        , HealthCareProfMapper mapper
        ) {
        this.repository = repository;
        this.factory = factory;
        this.mapper = mapper;   
    }
    @Override
    public HealthCareProfOutputDTO execute(UpdateHealthCareProfInputDTO inputDTO)
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
        HealthCareProf entity;
        HealthCareProf entityActivateStatus;
        try {
            HealthCareProfName name = new HealthCareProfName(inputDTO.name());
            HealthCareProfName surname = new HealthCareProfName(inputDTO.surname());
            HealthCareProfEmail email = new HealthCareProfEmail(inputDTO.email());
            HealthCareProfId id = new HealthCareProfId(inputDTO.id());
            // Create a new HealthCareProf object using the factory
            entity = factory.recreateExistingHealthCareProf(id, name, surname, email,null);
        // fetch existing HealthCareProf from the repository
        Optional<HealthCareProf> existingHealthCareProf = repository.findById(id);
        if (!existingHealthCareProf.isPresent()) {
            throw new DomainException("Medical Sales Representative not found.");
        }
        // Validate Unique Email
        if (!existingHealthCareProf.get().getEmail().equals(email)) {
            Optional<HealthCareProf> HealdCareProfRepWithEmail = repository.findByEmail(email);
            if (HealdCareProfRepWithEmail.isPresent()) {
                throw new DomainException("There is already a Medical Sales Representative with this email.");
            }
        }
        // keep the existing HealthCareProf active status
        if (Boolean.TRUE.equals(existingHealthCareProf.get().isActive())) {
            entityActivateStatus = entity.setActivate();
        } else {
            entityActivateStatus = entity.setDeactivate();
        }
        // Update the existing HealthCareProf with the new values
        repository.save(entityActivateStatus);
        // Convert response to output and return
        return mapper.outputFromEntity(entityActivateStatus);
        } catch (BusinessException | IllegalArgumentException  e) {
            throw new DomainException(e.getMessage());
        }
    }
}
