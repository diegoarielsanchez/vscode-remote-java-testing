package com.cleanddd.domain.healthcareprof;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfActive;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;

public final class HealthCareProfMother {
    public static HealthCareProf create(HealthCareProfId id, HealthCareProfName name, HealthCareProfName surname, HealthCareProfEmail email, HealthCareProfActive isActive) {
        return new HealthCareProf(id, name, surname, email, isActive);
    }
    public static HealthCareProf random() {
        return create(HealthCareProfId.random(),HealthCareProfNameMother.random(),HealthCareProfNameMother.random(), HealthCareProfEmailMother.random(), HealthCareProfActiveMother.random());
    }
    
}
