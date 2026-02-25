package com.das.cleanddd.domain.healthcareprof.usecases.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfRepository;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfIDDto;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfMapper;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public class GetHealthCareProfByIdUseCase implements UseCase<HealthCareProfIDDto, HealthCareProfOutputDTO> {

    @Autowired
    private final HealthCareProfRepository repository; 
    @Autowired
    private final HealthCareProfMapper mapper;

    public GetHealthCareProfByIdUseCase(HealthCareProfRepository repository
    , HealthCareProfMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;   
    }
    //@SuppressWarnings("unlikely-arg-type")
    @Override
    public HealthCareProfOutputDTO execute(HealthCareProfIDDto inputDTO) throws DomainException {
        
        if(inputDTO.id()==null) {
            throw new DomainException("Medical Sales Representative Id is required.");
          }
        HealthCareProfId id = new HealthCareProfId(inputDTO.id());
        Optional<HealthCareProf> entity = repository.findById(id   );
        if(!entity.isPresent()) {
            throw new DomainException("Medical Sales Representative not found.");
        }
        //return mapper.outputFromEntity(medicalSalesRep);
        return mapper.outputFromEntity(entity.get());
    }
}
