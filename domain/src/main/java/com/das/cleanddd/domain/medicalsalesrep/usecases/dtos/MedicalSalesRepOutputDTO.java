package com.das.cleanddd.domain.medicalsalesrep.usecases.dtos;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;

public record MedicalSalesRepOutputDTO(
  MedicalSalesRepId id,
  String name,
  String surname,
  String email
) {}
