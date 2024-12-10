/*
 A JavaBean is still a POJO but introduces a strict set of rules around how we implement it:

Access levels – our properties are private and we expose getters and setters
Method names – our getters and setters follow the getX and setX convention (in the case of a boolean, isX can be used for a getter)
Default Constructor – a no-argument constructor must be present so an instance can be created without providing arguments, for example during deserialization
Serializable – implementing the Serializable interface allows us to store the state

 */

package com.das.cleanddd.domain.share;

import java.io.Serializable;
import java.time.LocalDate;

public class PersonJavaBean implements Serializable {

    private static final long serialVersionUID = -3760445487636086034L;
    private String firstName;
    private String lastName;
    private LocalDate bornDate;

    public  PersonJavaBean() {
    }

    public PersonJavaBean(String firstName, String lastName, LocalDate bornDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornDate = bornDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //  additional getters/setters
}
