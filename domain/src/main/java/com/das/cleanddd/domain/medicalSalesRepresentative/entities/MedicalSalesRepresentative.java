package com.das.cleanddd.domain.medicalSalesRepresentative.entities;

import com.das.cleanddd.domain.shared.PersonJavaBean;

public class MedicalSalesRepresentative extends PersonJavaBean {

    //private final MedicalSalesRepresentativeId id;
/*     private final String    name;
    private final String    surname;
 */    private final String    email;
       private final Boolean isActive;

    public MedicalSalesRepresentative(String name, String surname, String email, Boolean isActive) {
        //this.id      = id;
        this.firstName    = name;
        this.lastName = surname;
        this.email   = email;
        this.isActive =  isActive;
    }

    /* 
    public MedicalSalesRepresentativeId id() {
        return id;
    }
    */
    public String name() {
        return firstName;
    }

    public String surname() {
        return lastName;
    }

    public String email() {
        return email;
    }

    public Boolean isActive() {
        return isActive;
    }
    public MedicalSalesRepresentativeId getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }
    public static MedicalSalesRepresentative create(String name, String surname, String email, Boolean isActive) {
        MedicalSalesRepresentative medicalSR = new MedicalSalesRepresentative(name, surname, email, isActive);

        //medicalSR.record(new CourseCreatedDomainEvent(id.value(), name.value(), duration.value()));

        return medicalSR;
    }

}
