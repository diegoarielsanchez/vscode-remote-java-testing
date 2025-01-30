package com.cleanddd.domain.medicalsalesrep;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.shared.WordMother;

public class MedicalSalesRepEmailMother {
    public static MedicalSalesRepEmail create(String value) {
        return new MedicalSalesRepEmail(value);
    }

    public static MedicalSalesRepEmail random() {
        return create(WordMother.random());
    }
}
