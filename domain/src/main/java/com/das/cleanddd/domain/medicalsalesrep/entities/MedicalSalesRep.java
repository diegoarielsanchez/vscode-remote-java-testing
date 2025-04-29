package com.das.cleanddd.domain.medicalsalesrep.entities;

import com.das.cleanddd.domain.shared.PersonJavaBean;
import com.das.cleanddd.domain.shared.UtilsFactory;
import com.das.cleanddd.domain.shared.ValidationUtils;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;


public class MedicalSalesRep extends PersonJavaBean {

    private final MedicalSalesRepId id;
/*     private final String    name;
    private final String    surname;
 */    private final MedicalSalesRepEmail    email;
       private final MedicalSalesRepActive active;
       private final ValidationUtils validationUtils;

    public MedicalSalesRep(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email2, MedicalSalesRepActive isActive) {
            this.id      = id;
            this.firstName    = name.toString();
            this.lastName = surname.toString();
            this.email   = email2;
            this.active =  isActive;
        this.validationUtils = (new UtilsFactory()).getValidationUtils();
    }


    public MedicalSalesRepId id() {
        return id;
    }

    public MedicalSalesRepName name() {
        return new MedicalSalesRepName(firstName) {
        } ;
    }

    public MedicalSalesRepName surname() {
        return new MedicalSalesRepName(lastName) {
        } ;
    }

    public MedicalSalesRepEmail email() {
        return email;
    }

    public MedicalSalesRepActive isActive() {
        return active;
    }
    public MedicalSalesRepId getId() {
        return this.id;
    }
    public static MedicalSalesRep create(MedicalSalesRepId id, MedicalSalesRepName name, MedicalSalesRepName surname, MedicalSalesRepEmail email2, MedicalSalesRepActive isActive) {
        return new MedicalSalesRep(id, name, surname, email2, isActive);
    }
 
    public void validate() throws BusinessException {
        if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
        if(this.validationUtils.isNullOrEmpty(this.firstName)) throw new RequiredFieldException("firstName");
        if(this.validationUtils.isNullOrEmpty(this.lastName)) throw new RequiredFieldException("lastName");
        if(this.validationUtils.isNullOrEmpty(this.email.toString())) throw new RequiredFieldException("email");
        //if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
        //this.address.validate();
        }


    public MedicalSalesRep activate() {
        return this.active.equals(Boolean.TRUE) ? this : this.activate();
    //return this.active.equals(Boolean.TRUE) ? this : this.withActive(true);
    }
    public MedicalSalesRep deactivate() {
        return this.active.equals(Boolean.FALSE) ? this : this.deactivate();
        //return this.active.equals(Boolean.TRUE) ? this : this.withActive(true);
    }
}
