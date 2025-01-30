package com.das.cleanddd.domain.medicalsalesrep.usecases.dtos;

public record CreateMedicalRepInputDTO(
  String id,
  String name,
  String surname,
  String email,
  Boolean active
) {}
