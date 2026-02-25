package com.cleanddd.domain.healthcareprof;

import java.util.Random;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfActive;

public class HealthCareProfActiveMother {
    public static HealthCareProfActive create(Boolean value) {
        return new HealthCareProfActive(value);
    }

    public static HealthCareProfActive random() {
        Random rd = new Random();
        return create(rd.nextBoolean());
    }
}

