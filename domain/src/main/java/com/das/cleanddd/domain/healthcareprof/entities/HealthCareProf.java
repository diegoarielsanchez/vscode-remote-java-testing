package com.das.cleanddd.domain.healthcareprof.entities;

import com.das.cleanddd.domain.shared.PersonJavaBean;
import com.das.cleanddd.domain.shared.UtilsFactory;
import com.das.cleanddd.domain.shared.ValidationUtils;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;


public class HealthCareProf extends PersonJavaBean {

    private final HealthCareProfId id;
    private final transient HealthCareProfEmail    email;
    private final transient HealthCareProfActive active;
    private final transient ValidationUtils validationUtils;

    public HealthCareProf(HealthCareProfId id, HealthCareProfName name, HealthCareProfName surname, HealthCareProfEmail email, HealthCareProfActive active) {
            this.id      = id == null ? HealthCareProfId.random() : id;
            this.firstName    = name.toString();
            this.lastName = surname.toString();
            this.email   = email == null ? new HealthCareProfEmail("") : email;
            this.active =  active == null ? new HealthCareProfActive(false) : active;
        this.validationUtils = (new UtilsFactory()).getValidationUtils();
    }


    public HealthCareProfId id() {
        return this.id;
    }

    public HealthCareProfName getName() {
        return new HealthCareProfName(firstName) {
        } ;
    }

    public HealthCareProfName getSurname() {
        return new HealthCareProfName(lastName) {
        } ;
    }

    public HealthCareProfEmail getEmail() {
        return this.email;
    }

    public HealthCareProfActive getActive() {
        return this.active;
    }
    public Boolean isActive() {
        return this.active != null && Boolean.TRUE.equals(this.active.value()) ? Boolean.TRUE : Boolean.FALSE;
    }
    public HealthCareProfId getId() {
        return this.id;
    }
    public static HealthCareProf create(HealthCareProfId id, HealthCareProfName name, HealthCareProfName surname, HealthCareProfEmail email, HealthCareProfActive active) {
        return new HealthCareProf(id, name, surname, email, active);
    }
 
    public void validate() throws BusinessException {
        if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
        if(this.validationUtils.isNullOrEmpty(this.firstName)) throw new RequiredFieldException("firstName");
        if(this.validationUtils.isNullOrEmpty(this.lastName)) throw new RequiredFieldException("lastName");
        if(this.validationUtils.isNullOrEmpty(this.email.toString())) throw new RequiredFieldException("email");
        //if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
        //this.address.validate();
        }


    public HealthCareProf setActivate() {
        return this.active != null && this.active.value() ? this : new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), this.email, new HealthCareProfActive(true));
    }
    public HealthCareProf setDeactivate() {
        return this.active != null && !this.active.value() ? this : new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), this.email, new HealthCareProfActive(false));
    }
}
