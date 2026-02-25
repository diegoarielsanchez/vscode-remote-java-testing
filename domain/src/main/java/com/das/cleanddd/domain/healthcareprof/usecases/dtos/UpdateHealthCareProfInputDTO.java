package com.das.cleanddd.domain.healthcareprof.usecases.dtos;

public record UpdateHealthCareProfInputDTO(
    String id,
    String name,
    String surname,
    String email
    ) {    
}
