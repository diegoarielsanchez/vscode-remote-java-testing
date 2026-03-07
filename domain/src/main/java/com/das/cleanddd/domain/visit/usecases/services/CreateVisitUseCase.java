package com.das.cleanddd.domain.visit.usecases.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfRepository;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.TextValueObject;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;
import com.das.cleanddd.domain.visit.IVisitRepository;
import com.das.cleanddd.domain.visit.entities.Visit;
import com.das.cleanddd.domain.visit.entities.VisitId;
import com.das.cleanddd.domain.visit.usecases.dtos.CreateVisitInputDTO;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitMapper;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitOutputDTO;

@Service
public final class CreateVisitUseCase implements UseCase<CreateVisitInputDTO, VisitOutputDTO> {

    @Autowired
    private final IVisitRepository visitRepository;
    @Autowired
    private final HealthCareProfRepository healthCareProfRepository;
    @Autowired
    private final MedicalSalesRepRepository medicalSalesRepRepository;
    @Autowired
    private final VisitMapper mapper;

    public CreateVisitUseCase(
        IVisitRepository visitRepository,
        HealthCareProfRepository healthCareProfRepository,
        MedicalSalesRepRepository medicalSalesRepRepository,
        VisitMapper mapper
    ) {
        this.visitRepository = visitRepository;
        this.healthCareProfRepository = healthCareProfRepository;
        this.medicalSalesRepRepository = medicalSalesRepRepository;
        this.mapper = mapper;
    }

    @Override
    public VisitOutputDTO execute(CreateVisitInputDTO inputDTO) throws DomainException {
        if (inputDTO == null) {
            throw new DomainException("Input DTO cannot be null");
        }
        if (inputDTO.visitDate() == null) {
            throw new DomainException("Visit date cannot be null");
        }
        if (inputDTO.healthCareProfId() == null || inputDTO.healthCareProfId().isBlank()) {
            throw new DomainException("Health Care Professional id cannot be null or empty");
        }
        if (inputDTO.visitSiteId() == null || inputDTO.visitSiteId().isBlank()) {
            throw new DomainException("Visit site id cannot be null or empty");
        }
        if (inputDTO.medicalSalesRepId() == null || inputDTO.medicalSalesRepId().isBlank()) {
            throw new DomainException("Medical Sales Representative id cannot be null or empty");
        }

        try {
            HealthCareProfId healthCareProfId = new HealthCareProfId(inputDTO.healthCareProfId());
            MedicalSalesRepId medicalSalesRepId = new MedicalSalesRepId(inputDTO.medicalSalesRepId());
            Identifier visitSiteId = new Identifier(inputDTO.visitSiteId()) {};

            Optional<HealthCareProf> healthCareProf = healthCareProfRepository.findById(healthCareProfId);
            if (healthCareProf.isEmpty()) {
                throw new DomainException("Health Care Professional not found");
            }

            Optional<MedicalSalesRep> medicalSalesRep = medicalSalesRepRepository.findById(medicalSalesRepId);
            if (medicalSalesRep.isEmpty()) {
                throw new DomainException("Medical Sales Representative not found");
            }

            TextValueObject comments = inputDTO.visitComments() == null
                ? null
                : new TextValueObject(inputDTO.visitComments()) {};

            Visit visit = new Visit(
                new VisitId(UUID.randomUUID().toString()),
                inputDTO.visitDate(),
                healthCareProf.get(),
                comments,
                visitSiteId,
                List.of(),
                medicalSalesRep.get()
            );

            visitRepository.save(visit);
            return mapper.outputFromEntity(visit);
        } catch (IllegalArgumentException e) {
            throw new DomainException(e.getMessage());
        }
    }
}
