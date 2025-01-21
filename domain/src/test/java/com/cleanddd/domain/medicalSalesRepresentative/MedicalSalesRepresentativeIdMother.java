package com.cleanddd.domain.medicalSalesRepresentative;

import com.cleanddd.domain.shared.UuidMother;
import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentativeId;

public final class MedicalSalesRepresentativeIdMother {
    public static MedicalSalesRepresentativeId create(String value) {
        return new MedicalSalesRepresentativeId(value);
    }

    public static MedicalSalesRepresentativeId random() {
        return create(UuidMother.random());
    }
}