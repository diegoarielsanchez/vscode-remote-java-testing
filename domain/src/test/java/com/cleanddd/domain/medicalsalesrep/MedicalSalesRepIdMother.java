package com.cleanddd.domain.medicalsalesrep;

import com.cleanddd.domain.shared.UuidMother;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;

public final class MedicalSalesRepIdMother {
    public static MedicalSalesRepId create(String value) {
        return new MedicalSalesRepId(value);
    }

    public static MedicalSalesRepId random() {
        return create(UuidMother.random());
    }
}