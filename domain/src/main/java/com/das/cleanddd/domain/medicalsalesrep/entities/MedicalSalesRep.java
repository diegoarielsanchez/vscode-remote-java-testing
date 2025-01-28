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
 */    private final String    email;
       private final Boolean active;
       private final ValidationUtils validationUtils;

    public MedicalSalesRep(MedicalSalesRepId id, String name, String surname, String email, Boolean isActive) {
        this.id      = id;
        this.firstName    = name;
        this.lastName = surname;
        this.email   = email;
        this.active =  isActive;
        this.validationUtils = (new UtilsFactory()).getValidationUtils();
    }


    public MedicalSalesRepId id() {
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

    public Boolean isActive() {
        return active;
    }
    public MedicalSalesRepId getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }
    public static MedicalSalesRep create(MedicalSalesRepId id, String name, String surname, String email, Boolean isActive) {
        MedicalSalesRep medicalSR = new MedicalSalesRep(id, name, surname, email, isActive);

        //medicalSR.record(new CourseCreatedDomainEvent(id.value(), name.value(), duration.value()));

        return medicalSR;
    }
    //@Override
/*     public MedicalSalesRep changeName(String newName) throws BusinessException {
        MedicalSalesRep c = this.firstName.equals(newName) ? this : this.withName(newName);
        c.validate();
        return c;
    }
 */
/*     public MedicalSalesRep changeSurname(String newSurname) throws BusinessException {
        MedicalSalesRep c = this.firstName.equals(newSurname) ? this : this.withName(newSurname);
        c.validate();
        return c;
    }
 */    //@Override
/*     public MedicalSalesRep activate() {
      return this.active.equals(Boolean.TRUE) ? this : this.withActive(true);
    }
 */
    //@Override
/*     public MedicalSalesRep deactivate() {
      return this.active.equals(Boolean.FALSE) ? this : this.withActive(false);
      
    }
 */    //@Override
/*     public MedicalSalesRep changeEmail(String newEmail) throws BusinessException {
      DefaultMedicalSalesRep c = this.email.equals(newEmail) ? this : this.withEmail(newEmail);
      c.validate();
      return c;
    }
 */
  //@Override
  public void validate() throws BusinessException {
    if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
    if(this.validationUtils.isNullOrEmpty(this.firstName)) throw new RequiredFieldException("firstName");
    if(this.validationUtils.isNullOrEmpty(this.lastName)) throw new RequiredFieldException("lastName");
    if(this.validationUtils.isNullOrEmpty(this.email)) throw new RequiredFieldException("email");
    //if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
    //this.address.validate();
  }


public MedicalSalesRep activate() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'activate'");
}
}
