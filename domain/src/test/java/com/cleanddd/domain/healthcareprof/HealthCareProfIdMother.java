package com.cleanddd.domain.healthcareprof;

import com.cleanddd.domain.shared.UuidMother;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;

public final class HealthCareProfIdMother {
    public static HealthCareProfId create(String value) {
        return new HealthCareProfId(value);
    }

    public static HealthCareProfId random() {
        return create(UuidMother.random());
    }
}