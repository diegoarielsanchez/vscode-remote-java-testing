package com.cleanddd.domain.healthcareprof;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.shared.MotherCreator;

public final class HealthCareProfNameMother {
    public static HealthCareProfName create(String value) {
        return new HealthCareProfName(value);
    }

    public static HealthCareProfName random() {
        return create(MotherCreator.random().name().firstName());
    }
}
