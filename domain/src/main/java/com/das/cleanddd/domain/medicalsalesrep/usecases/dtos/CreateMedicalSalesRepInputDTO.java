package com.das.cleanddd.domain.medicalsalesrep.usecases.dtos;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;

public record CreateMedicalSalesRepInputDTO(
  MedicalSalesRepId id,
  MedicalSalesRepName name,
  MedicalSalesRepName surname,
  MedicalSalesRepEmail email
  //,
  //MedicalSalesRepActive active
) {}
