package com.das.cleanddd.domain.medicalSalesRepresentative.usecases.dtos;

public record CreateMedicalRepresentativeInputDTO(
  String name,
  String surname,
  String email
) {}
