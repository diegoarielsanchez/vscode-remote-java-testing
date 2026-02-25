package com.cleanddd.domain.healthcareprof;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.shared.WordMother;

public class HealthCareProfEmailMother {
    public static HealthCareProfEmail create(String value) {
        return new HealthCareProfEmail(value);
    }

    public static HealthCareProfEmail random() {
        return create(WordMother.random());
    }
}
