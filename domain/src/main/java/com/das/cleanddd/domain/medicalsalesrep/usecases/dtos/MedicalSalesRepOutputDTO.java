package com.das.cleanddd.domain.medicalsalesrep.usecases.dtos;

import java.util.UUID;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;

public record MedicalSalesRepOutputDTO(
  MedicalSalesRepId id,
  String name,
  String surname,
  String email
) {}
