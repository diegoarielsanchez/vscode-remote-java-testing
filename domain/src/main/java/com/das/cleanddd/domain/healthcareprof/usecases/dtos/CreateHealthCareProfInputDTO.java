package com.das.cleanddd.domain.healthcareprof.usecases.dtos;

public record CreateHealthCareProfInputDTO(
  String name,
  String surname,
  String email
) {}
