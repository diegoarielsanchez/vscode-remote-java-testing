package com.das.cleanddd.domain.share;

import java.time.LocalDate;

public class PersonPOJO {
   private String firstName;
    private String lastName;
    private LocalDate bornDate;

    public PersonPOJO(String firstName, String lastName, LocalDate bornDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornDate = bornDate;
    }

    public String name() {
        return this.firstName + " " + this.lastName;
    }

    public LocalDate getStart() {
        return this.bornDate;
    }
}
