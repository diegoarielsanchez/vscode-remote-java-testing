package com.das.cleanddd.domain.medicalSalesRepresentative.entities;

import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;

public class MedicalSalesRepresentativeFactory {

    public MedicalSalesRepresentative createMedicalSalesRepresentative(String name, String surname, String email, Boolean isActive) throws BusinessException {
      return new DefaultMedicalSalesRepresentative(name, surname, email, isActive);
    }
  
    public MedicalSalesRepresentative recreateExistingMedicalSalesRepresentative(MedicalSalesRepresentativeId id, String name, String email, Boolean active, Address address) throws BusinessException {
      if (id == null) {
        throw new RequiredFieldException("id");
      }
  
      MedicalSalesRepresentative existingMedicalSalesRepresentative = new DefaultMedicalSalesRepresentative(id, name, email, address);
  
      return keepActiveValueForExistingMedicalSalesRepresentative(existingMedicalSalesRepresentative, active);
    }
  
    MedicalSalesRepresentative keepActiveValueForExistingMedicalSalesRepresentative(MedicalSalesRepresentative existingMedicalSalesRepresentative, Boolean activeValue) {
/*       if(activeValue!=null) {
        if(Boolean.TRUE.equals(activeValue)) return existingMedicalSalesRepresentative.activate();
        else return existingMedicalSalesRepresentative.deactivate();
      }
 */      return existingMedicalSalesRepresentative;
    }

  }
