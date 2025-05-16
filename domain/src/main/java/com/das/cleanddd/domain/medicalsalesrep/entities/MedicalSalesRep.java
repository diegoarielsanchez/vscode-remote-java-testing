package com.das.cleanddd.domain.medicalsalesrep.entities;

import com.das.cleanddd.domain.shared.PersonJavaBean;
import com.das.cleanddd.domain.shared.UtilsFactory;
import com.das.cleanddd.domain.shared.ValidationUtils;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;


public class MedicalSalesRep extends PersonJavaBean {

    private final MedicalSalesRepId id;
    private final transient MedicalSalesRepEmail    email;
    private final transient MedicalSalesRepActive active;
    private final transient ValidationUtils validationUtils;

    public MedicalSalesRep(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email, MedicalSalesRepActive active) {
            this.id      = id == null ? MedicalSalesRepId.random() : id;
            this.firstName    = name.toString();
            this.lastName = surname.toString();
            this.email   = email == null ? new MedicalSalesRepEmail("") : email;
            this.active =  active == null ? new MedicalSalesRepActive(false) : active;
        this.validationUtils = (new UtilsFactory()).getValidationUtils();
    }


    public MedicalSalesRepId id() {
        return this.id;
    }

    public MedicalSalesRepName getName() {
        return new MedicalSalesRepName(firstName) {
        } ;
    }

    public MedicalSalesRepName getSurname() {
        return new MedicalSalesRepName(lastName) {
        } ;
    }

    public MedicalSalesRepEmail getEmail() {
        return this.email;
    }

    public MedicalSalesRepActive getActive() {
        return this.active;
    }
    public Boolean isActive() {
        return this.active != null && Boolean.TRUE.equals(this.active.value()) ? Boolean.TRUE : Boolean.FALSE;
    }
    public MedicalSalesRepId getId() {
        return this.id;
    }
    public static MedicalSalesRep create(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email, MedicalSalesRepActive active) {
        return new MedicalSalesRep(id, name, surname, email, active);
    }
 
    public void validate() throws BusinessException {
        if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
        if(this.validationUtils.isNullOrEmpty(this.firstName)) throw new RequiredFieldException("firstName");
        if(this.validationUtils.isNullOrEmpty(this.lastName)) throw new RequiredFieldException("lastName");
        if(this.validationUtils.isNullOrEmpty(this.email.toString())) throw new RequiredFieldException("email");
        //if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
        //this.address.validate();
        }


    public MedicalSalesRep setActivate() {
        return this.active != null && this.active.value() ? this : new MedicalSalesRep(this.id, new MedicalSalesRepName(this.firstName), new MedicalSalesRepName(this.lastName), this.email, new MedicalSalesRepActive(true));
    }
    public MedicalSalesRep setDeactivate() {
        return this.active != null && !this.active.value() ? this : new MedicalSalesRep(this.id, new MedicalSalesRepName(this.firstName), new MedicalSalesRepName(this.lastName), this.email, new MedicalSalesRepActive(false));
    }
}
