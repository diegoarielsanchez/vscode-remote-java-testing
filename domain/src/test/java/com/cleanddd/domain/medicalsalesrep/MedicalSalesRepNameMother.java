package com.cleanddd.domain.medicalsalesrep;

import com.cleanddd.domain.shared.UuidMother;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;

public final class MedicalSalesRepNameMother {
    public static MedicalSalesRepName create(String value) {
        return new MedicalSalesRepName(value);
    }

    public static MedicalSalesRepName random() {
        return create(UuidMother.random());
    }
}
