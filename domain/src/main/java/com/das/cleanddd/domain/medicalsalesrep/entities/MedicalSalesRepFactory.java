package com.das.cleanddd.domain.medicalsalesrep.entities;

import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;

public class MedicalSalesRepFactory {

    public MedicalSalesRep createMedicalSalesRepresentative(String name, String surname, String email, Boolean isActive) throws BusinessException {
      return new DefaultMedicalSalesRep(MedicalSalesRepId.random(), name, surname, email, isActive);
    }
  
    public MedicalSalesRep recreateExistingMedicalSalesRepresentative(MedicalSalesRepId id, String name, String surname, String email, Boolean isActive) throws BusinessException {
      if (id == null) {
        throw new RequiredFieldException("id");
      }
  
      MedicalSalesRep existingMedicalSalesRepresentative = new DefaultMedicalSalesRep(id, name, surname, email, isActive);
  
      return keepActiveValueForExistingMedicalSalesRepresentative(existingMedicalSalesRepresentative, isActive);
    }
  
    MedicalSalesRep keepActiveValueForExistingMedicalSalesRepresentative(MedicalSalesRep existingMedicalSalesRepresentative, Boolean activeValue) {
       if(activeValue!=null) {
        if(Boolean.TRUE.equals(activeValue)) return existingMedicalSalesRepresentative.activate();
        else return existingMedicalSalesRepresentative.deactivate();
      }
      return existingMedicalSalesRepresentative;
    }

  }
