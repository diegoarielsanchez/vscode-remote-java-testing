package com.cleanddd.domain.medicalsalesrep;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.shared.WordMother;

public final class MedicalSalesRepNameMother {
    public static MedicalSalesRepName create(String value) {
        return new MedicalSalesRepName(value);
    }

    public static MedicalSalesRepName random() {
        return create(WordMother.random());
    }
}
