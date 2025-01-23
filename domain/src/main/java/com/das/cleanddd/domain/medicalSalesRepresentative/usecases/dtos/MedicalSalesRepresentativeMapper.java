package com.das.cleanddd.domain.medicalSalesRepresentative.usecases.dtos;

import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentative;

public class MedicalSalesRepresentativeMapper {

    public MedicalSalesRepresentativeOutputDTO outputFromEntity(MedicalSalesRepresentative c) {
      return new MedicalSalesRepresentativeOutputDTO(
        c.getId(),
        c.getFirstName(),
        c.getLastName(),
        c.email()
      );
    }
  }