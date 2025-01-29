package com.das.cleanddd.domain.medicalsalesrep.entities;

import com.das.cleanddd.domain.shared.BoolValueObject;
import com.das.cleanddd.domain.shared.StringValueObject;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;

public class MedicalSalesRepFactory {

    public MedicalSalesRep createMedicalSalesRepresentative(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email2, MedicalSalesRepActive isActive) throws BusinessException {
      return new DefaultMedicalSalesRep(MedicalSalesRepId.random(), name, surname, email2, isActive);
    }
  
    public MedicalSalesRep recreateExistingMedicalSalesRepresentative(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email2, MedicalSalesRepActive isActive) throws BusinessException {
      if (id == null) {
        throw new RequiredFieldException("id");
      }
  
      MedicalSalesRep existingMedicalSalesRepresentative = new DefaultMedicalSalesRep(id, name, surname, email2, isActive);
  
      return keepActiveValueForExistingMedicalSalesRepresentative(existingMedicalSalesRepresentative, isActive);
    }
  
    MedicalSalesRep keepActiveValueForExistingMedicalSalesRepresentative(MedicalSalesRep existingMedicalSalesRepresentative, MedicalSalesRepActive activeValue) {
       if(activeValue!=null) {
        if(Boolean.TRUE.equals(activeValue)) return existingMedicalSalesRepresentative.activate();
        else return existingMedicalSalesRepresentative.activate();
      }
      return existingMedicalSalesRepresentative;
    }

  }
