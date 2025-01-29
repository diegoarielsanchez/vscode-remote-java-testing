package com.cleanddd.domain.medicalsalesrep;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepActive;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;

public final class MedicalSalesRepMother {
    public static MedicalSalesRep create(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email, MedicalSalesRepActive isActive) {
        return new MedicalSalesRep(id, name, surname, email, isActive);
    }
    public static MedicalSalesRep random() {
        return create(MedicalSalesRepId.random(),MedicalSalesRepNameMother.random(),MedicalSalesRepNameMother.random(), MedicalSalesRepEmailMother.random(), MedicalSalesRepActiveMother.random())
    }
    
}
