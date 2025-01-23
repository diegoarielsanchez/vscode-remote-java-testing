package com.das.cleanddd.domain.medicalSalesRepresentative.usecases.dtos;

import java.util.UUID;

import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentativeId;

public record MedicalSalesRepresentativeOutputDTO(
  MedicalSalesRepresentativeId id,
  String name,
  String surname,
  String email
) {}
