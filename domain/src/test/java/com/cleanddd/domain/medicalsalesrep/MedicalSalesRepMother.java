package com.cleanddd.domain.medicalsalesrep;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;

public final class MedicalSalesRepMother {
    public static MedicalSalesRep create(MedicalSalesRepId id, String name, String surname, String email, Boolean isActive) {
        return new MedicalSalesRep(id, name, surname, email, isActive);
    }
    public static MedicalSalesRep random() {
        return create(MedicalSalesRepId.random(),MedicalSalesRepNameMother.random(),MedicalSalesRepNameMother.random(), MedicalSalesEMail.random(), MedicalSalesActive.random())
    }
    
}
