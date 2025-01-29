package com.cleanddd.domain.medicalsalesrep;

import java.util.Random;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepActive;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.shared.WordMother;

public class MedicalSalesRepActiveMother {
    public static MedicalSalesRepActive create(Boolean value) {
        return new MedicalSalesRepActive(value);
    }

    public static MedicalSalesRepActive random() {
        Random rd = new Random();
        return create(rd.nextBoolean());
    }

