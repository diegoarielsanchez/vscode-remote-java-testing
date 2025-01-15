package com.das.cleanddd.domain.medicalSalesRepresentative;

import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.PersonJavaBean;

public class MedicalSalesRepresentative extends PersonJavaBean {

    private final Identifier id;
/*     private final String    name;
    private final String    surname;
 */    private final String    email;

    public MedicalSalesRepresentative(Identifier id, String name, String surname, String email) {
        this.id      = id;
        this.firstName    = name;
        this.lastName = surname;
        this.email   = email;
    }

    public Identifier id() {
        return id;
    }

    public String name() {
        return firstName;
    }

    public String surname() {
        return lastName;
    }

    public String email() {
        return email;
    }
}
