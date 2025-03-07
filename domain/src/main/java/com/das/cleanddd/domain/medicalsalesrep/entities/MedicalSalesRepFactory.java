package com.das.cleanddd.domain.medicalsalesrep.entities;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;

@Service
public class MedicalSalesRepFactory {

    public MedicalSalesRep createMedicalSalesRep(MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email2) throws BusinessException {
      return new DefaultMedicalSalesRep(MedicalSalesRepId.random(), name, surname, email2);
    }
  
    public MedicalSalesRep recreateExistingMedicalSalesRepresentative(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email2, MedicalSalesRepActive isActive) throws BusinessException {
      if (id == null) {
        throw new RequiredFieldException("id");
      }
  
      MedicalSalesRep existingMedicalSalesRepresentative = new DefaultMedicalSalesRep(id, name, surname, email2);
  
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
