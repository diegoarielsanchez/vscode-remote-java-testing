package com.das.cleanddd.domain.medicalsalesrep.usecases.dtos;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;

@Service
public class MedicalSalesRepMapper {

    public MedicalSalesRepOutputDTO outputFromEntity(MedicalSalesRep medicalSalesRep) {
      return new MedicalSalesRepOutputDTO(
        medicalSalesRep.id().value(),
        medicalSalesRep.name().value(),
        medicalSalesRep.surname().value(),
        medicalSalesRep.getEmail().value(),
        medicalSalesRep.active().value()
      );
    }
  }