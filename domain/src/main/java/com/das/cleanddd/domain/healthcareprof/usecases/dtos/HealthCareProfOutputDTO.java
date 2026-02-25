package com.das.cleanddd.domain.healthcareprof.usecases.dtos;

public record HealthCareProfOutputDTO(
  String id,
  String name,
  String surname,
  String email,
  Boolean active
) {}
