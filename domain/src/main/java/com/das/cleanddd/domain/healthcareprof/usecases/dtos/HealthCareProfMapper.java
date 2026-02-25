package com.das.cleanddd.domain.healthcareprof.usecases.dtos;

import java.util.List;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;

@Service
public class HealthCareProfMapper {

    public HealthCareProfOutputDTO outputFromEntity(HealthCareProf entity) {
      return new HealthCareProfOutputDTO(
        entity.id().value(),
        entity.getName().value(),
        entity.getSurname().value(),
        entity.getEmail().value(),
        entity.getActive().value()
      );
    }
    
    public List<HealthCareProfOutputDTO> outputFromEntityList(List<HealthCareProf> entities) {
        return entities.stream()
            .map(this::outputFromEntity)
            .toList();
    }
  }