package com.das.cleanddd.domain.medicalsalesrep.usecases.dtos;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;

public class MedicalSalesRepMapper {

    public MedicalSalesRepOutputDTO outputFromEntity(MedicalSalesRep c) {
      return new MedicalSalesRepOutputDTO(
        c.getId(),
        c.getFirstName(),
        c.getLastName(),
        c.email()
      );
    }
  }